# Individual Task: Library

This folder contains a separate client-server application for the individual assignment.

## Structure

- `server` - Spring Boot 3 REST API
- `client` - Vue 3 application
- `postman` - Postman collection for testing REST endpoints

## Server

Run from `ind_zavd/server`.

```bash
mvn spring-boot:run
```

The API runs on `http://localhost:8082`.

## Client

Run from `ind_zavd/client`.

```bash
npm install
npm run dev
```

The client runs on `http://localhost:5173`.

## API

- `GET /api/categories`
- `POST /api/categories`
- `PUT /api/categories/{id}`
- `DELETE /api/categories/{id}`
- `GET /api/books`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`

