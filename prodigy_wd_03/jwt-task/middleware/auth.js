const jwt = require('jsonwebtoken');
require('dotenv').config();

/**
 * auth(roles) protects routes and (optionally) enforces role-based access.
 * Pass a single role string or an array of roles. Example: auth('admin') or auth(['admin','owner'])
 */
const auth = (roles = []) => {
  if (typeof roles === 'string') roles = [roles];

  return (req, res, next) => {
    const hdr = req.header('Authorization');
    const token = hdr && hdr.startsWith('Bearer ') ? hdr.split(' ')[1] : null;
    if (!token) return res.status(401).json({ message: 'Access denied. No token provided.' });

    try {
      const decoded = jwt.verify(token, process.env.JWT_SECRET);
      req.user = decoded; // { _id, username, role }
      if (roles.length && !roles.includes(decoded.role)) {
        return res.status(403).json({ message: 'Forbidden: insufficient role' });
      }
      next();
    } catch (err) {
      return res.status(401).json({ message: 'Invalid or expired token' });
    }
  };
};

module.exports = auth;
