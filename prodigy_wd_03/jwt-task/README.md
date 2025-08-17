# Prodigy InfoTech - Task 03
JWT-Based Authentication & Authorization (Node.js + Express + MongoDB)

## Quick Start
1. Install Node.js (LTS) and MongoDB (or use MongoDB Atlas).
2. Extract this project and open a terminal in the folder.
3. Install dependencies:
   ```bash
   npm install
   ```
4. Create a `.env` by copying `.env.example` and updating values.
5. Run:
   ```bash
   npm start
   ```
6. Test with curl or Postman.

## Endpoints
- `POST /register` — `{ "username": "alice", "password": "pass123", "role": "admin" }`
- `POST /login` — returns `{ token }`
- `GET /profile` — header `Authorization: Bearer <token>`
- `GET /admin` — admin role only
- `GET /owner-zone` — owner or admin

## Notes
- Passwords stored using bcrypt.
- JWT expires in 1 hour; adjust in `server.js`.
