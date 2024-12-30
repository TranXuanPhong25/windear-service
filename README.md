# [Library Management System](https://windear.vercel.app/) - Windear Team

## Authors

1. 23021657 - [Trần Xuân Phong](https://github.com/TranXuanPhong25)
2. 23021669 - [Bùi Minh Quang](https://github.com/mquang279)
3. 23021673 - [Phạm Minh Quân](https://github.com/quanpm2008)

## Disclaim
This project use Goodreads API with their public key only for educational purpose.

## Description

The Library Management Web Application is a robust, user-friendly platform developed using Spring Boot to streamline library operations and enhance user experience. This web-based solution enables efficient management of books, users, and library transactions with a focus on automation and accessibility.
This project consists of two main components:

1. Backend: A Spring Boot application for managing the library system's core functionality.
2. Frontend: A TypeScript-based web application for user interaction and management.

## Features

### Backend (Spring Boot)

- User Management: Create, retrieve, update, and delete users.
- Book Management: Add, retrieve, update, and delete books.
- Borrow and Return Management: Track borrowing and returning of books.
- Role-Based Access Control: Different roles for admins and users.
- Database Integration: Persistent storage using PostgreSQL and MongoDB.

### Frontend (Reactjs + Typescript)

- User-Friendly UI: Modern, responsive design using React or Angular.
- Dynamic Forms: Add, update, or delete users and books.
- Interactive Dashboards: View real-time data about books, users, and borrow history.
- Role-Based Views: Different UI for admin and users.
- Error Handling: Display clear messages for validation and server errors.

## Project Structure

```
src/main/java/com/windear/app
├── config           # Application and security configuration files
├── controller       # API endpoints
├── dto              # Data Transfer Objects for API communication
├── entity           # JPA entities representing database tables
├── exception        # Custom exceptions and global error handling
├── model            # Domain-specific models for internal use
├── primarykey       # Classes for composite primary keys
├── repository       # Interfaces for database interaction
└── service          # Business logic and service layer

```

## Technologies Used

### Backend

- Spring Boot: Framework for backend development.
- Spring Data JPA: ORM for database interaction.
- Spring Security: Authentication and authorization.
- PostgreSQL and MongoDB: Database for data storage.
- Redis: Caching system for improved performance and data retrieval.
- Integrate with the Auth0 provider to ensure reliability and consistency.

## Future Enhancements

- Search Filters: Advanced search and sorting options.
- Mobile App Integration: Develop a companion mobile app for users to access library services on the go.
- Multilingual Support: Localize the frontend and backend for multiple languages to cater to diverse users.
- Email and SMS Notifications: Send automatic reminders for due dates, overdue books, or upcoming library events.
- Notification: Soft delete and undo deletion notification.
- More analytic service for providing more intuitive insight.
