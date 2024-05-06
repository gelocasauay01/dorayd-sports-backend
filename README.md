# Dorayd Sports Backend Software

## Overview
The Dorayd Sports Backend Software is a Spring Boot application designed to manage player statistics for basketball leagues. It provides a robust and efficient way of tracking, storing, and retrieving player data, including points, rebounds, assists, and more.

## Features
- Player Profile Management: Create, update, and delete player profiles.
- Statistics Tracking: Track and update player statistics after each game.
- League Management: Manage different basketball leagues and their respective teams.

## Setup and Configuration

### Prerequisites
- Java 17
- Maven
- PostgreSQL

### Steps

1. **Clone the repository**
    ```
    git clone https://github.com/gelocasauay01/dorayd-sports-backend.git
    cd dorayd-sports-backend
    ```

2. **Update the application.yml file**
   Navigate to `src/main/resources` and open `application.yml`. Update the following fields with your local database configuration:
    ```yaml
    spring:
      datasource:
        url: <jdbc_url>
        username: <your_username>
        password: <your_password>
    ```

3. **Run the application**
    ```
    mvn spring-boot:run
    ```

## Usage
Once the server is running, you can access the API endpoints at `http://localhost:8080/api/`.
