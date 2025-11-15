a

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

WhatToRead is a Spring Boot application for managing a personal paper reading list. It allows users to store, search, and track academic papers with ratings, notes, and metadata.

**Tech Stack:**
- Spring Boot 3.5.3 with Java 17
- JTE (Java Template Engine) for server-side rendering
- PostgreSQL database with JPA/Hibernate
- Flyway for database migrations
- Maven for build management
- Docker Compose for local development

## Development Commands

### Build and Run
```bash
# Build the application
./mvnw clean compile

# Run tests
./mvnw test

# Run the application locally
./mvnw spring-boot:run

# Package the application
./mvnw package
```

### Database Setup
```bash
# Start PostgreSQL and run migrations
docker-compose up

# The application expects POSTGRES_PASSWORD environment variable to be set
export POSTGRES_PASSWORD=your_password
```

### Testing
```bash
# Run all tests
./mvnw test

# Run specific test class
./mvnw test -Dtest=HomeControllerErrorHandlingTest

# Run integration tests
./mvnw test -Dtest="*IntegrationTest"
```

## Architecture

### Database Layer
- **Repository Pattern**: Uses Spring Data JPA repositories (`PaperListQ`, `PaperDetailsQ`)
- **Database Views**: Application uses PostgreSQL views for optimized queries
- **Migrations**: Flyway manages schema changes in `src/main/resources/db/migration/`
- **Test Database**: Separate test database configuration in `application-test.properties`

### Service Layer
- **PaperService**: Core business logic for paper management operations
- **Error Handling**: Custom `PaperNotFoundException` with proper error pages

### Web Layer
- **HomeController**: Single controller handling all web endpoints
- **JTE Templates**: Server-side rendered templates in `src/main/jte/`
- **Error Pages**: Custom error handling with dedicated templates in `jte/error/`

### Key Models
- **PaperListEntry**: Main paper entity with title, authors, tags, ratings
- **PaperDetails**: Extended view with additional metadata
- **Page**: Pagination wrapper for search results

## Important Configuration

### JTE Template Engine
- **Development Mode**: `gg.jte.development-mode=true` in application.properties
- **Template Location**: `src/main/jte/`
- **Template Compilation**: Maven plugin pre-compiles templates for production

### Database Configuration
- **Main Database**: PostgreSQL on port 5432
- **Test Database**: Separate postgres_test database
- **Connection**: Uses environment variable `POSTGRES_PASSWORD`

### Testing Strategy
- **Test Database**: Uses `create-drop` DDL strategy with Flyway migrations
- **Integration Tests**: Full Spring context with real database
- **Error Handling Tests**: Comprehensive coverage of exception scenarios

## Development Workflow

1. **Database Changes**: Add migrations to `src/main/resources/db/migration/`
2. **View Updates**: Database views are used for complex queries (paper_list_view, paper_details_view)
3. **Template Changes**: JTE templates auto-reload in development mode
4. **Testing**: Always run full test suite before changes - tests use separate database

## Scripts and Utilities

- **scripts/**: Python utilities for data generation and database setup
- **TODOS/**: Feature specifications and project planning documents