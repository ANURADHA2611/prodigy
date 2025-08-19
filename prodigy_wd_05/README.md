# Task 05 — Hotel Booking Platform: Backend API (Mini Project)

A minimal Spring Boot API that supports:
- User registration & JWT login
- CRUD for hotel room listings by the authenticated owner
- Search available rooms for a date range
- Booking rooms with conflict checking
- Input validation and global error handling
- H2 (default) or MySQL (profile: `mysql`) database

## Quick Start (H2 - no setup)

```bash
./mvnw spring-boot:run
```

Then test the endpoints:

1. **Register**  
`POST /api/auth/register`  
```json
{"username":"anuradha","password":"secret"}
```

2. **Login** (get JWT)  
`POST /api/auth/login` -> copy `token` from response

3. **Create Room (auth required)**  
`POST /api/rooms` with header `Authorization: Bearer <token>`  
```json
{"name":"Sea View","type":"Deluxe","capacity":2,"pricePerNight":1500}
```

4. **Search Available Rooms** (public)  
`GET /api/rooms/search?checkIn=2025-08-20&checkOut=2025-08-22`

5. **Book a Room (auth)**  
`POST /api/bookings`  
```json
{"roomId":1,"checkIn":"2025-08-20","checkOut":"2025-08-22"}
```

6. **My Bookings (auth)**  
`GET /api/bookings/me`

7. **Cancel Booking (auth)**  
`POST /api/bookings/{id}/cancel`

## Switch to MySQL

1. Update `src/main/resources/application-mysql.properties` with your credentials.
2. Run with profile:  
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

## Notes
- Passwords are hashed with BCrypt.
- Replace the demo JWT secret in `JwtService` for production.
- This is intentionally compact to fit the mini-project scope.
