# Music with Vibe

A full-stack music web application using:

- Java Spring Boot backend
- Firebase Authentication & Firestore
- Audiomack music search API
- YouTube video previews
- Frontend HTML/CSS/JS

## Setup

### Backend

1. Place your `serviceAccountKey.json` in `backend/src/main/resources/`.
2. Add your YouTube API key in `MusicController.java`.
3. Build and run:
```bash
cd backend
mvn spring-boot:run
