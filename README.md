# Spring Rest API Project

This project demonstrates a Spring Boot REST API for battery management. It includes features like authentication, validation.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Testing](#testing)
- [Health Check and Metrics](#Health-check-and-Metrics)

## Introduction

This Spring Boot project provides an API for managing battery information. It allows users to create, retrieve, and manage batteries, as well as authenticating users and enforcing rate limits.

## Features

- User authentication using JWT (JSON Web Token)
- Battery management endpoints (create, retrieve)
- Validation of battery data (name, postcode, capacity)
- Endpoints for health checks and metrics
- Documentation using Swagger UI

## Getting Started

1. Clone the repository:
   ```shell
   git clone https://github.com/your-username/spring-rest-api-project.git
   ```
2. Navigate to the project directory
   ```shell
   cd spring-rest-api-project
   ```
3. Build and run the project using Gradle:
   ```shell
   ./gradlew bootRun
   ```
## Usage
Once the project is running, you can access the API endpoints using tools like cURL, Postman, or Swagger UI. Remember to include the necessary headers for authentication and other required information.

## API Documentation
API documentation is available through Swagger UI. Open your browser and navigate to http://localhost:8080/swagger-ui.html to explore and test the API endpoints.

## Configuration
The project configuration can be customized in the `application.properties` file. This includes settings for the database, authentication, rate limiting, and other application properties.

## Health Check and Metrics
- You can check the health and metrics of this service by using the following endpoints:
   - `/actuator/metrics`
   - `/actuator/health`
- Please ensure that you include a valid JWT token in the headers of your requests for authentication.
## Testing
   ```shell
   ./gradlew test
   ```

