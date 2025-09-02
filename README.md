# Platform Monorepo Project

This project contains a suite of enterprise applications built on a common platform. It is structured as a Gradle multi-module project.

## Project Structure

The project is divided into two types of modules:

-   **Common Modules (`common-*`)**: These are library modules that provide shared functionality to all applications.
    -   `common-core`: Core utilities and base classes. (Currently a placeholder)
    -   `common-web`: Shared web components, such as the default Thymeleaf layout.
    -   `common-security`: Handles user authentication, authorization, and provides core entities like `User`, `Role`, and `Dept`.
-   **Application Modules (`*10`)**: These are runnable Spring Boot applications that solve specific business problems.
    -   `cmms10`: A Computerized Maintenance Management System. This is the primary implemented application.
    -   `platform10`: (Not yet implemented) Intended as an admin portal for managing users, companies, etc.
    -   Other modules like `wflow10`, `market10` are planned.

## Build Instructions

This is a standard Gradle project.

### Prerequisites
- Java 24 JDK

### Build Command
To build all modules and run all tests, execute the following command from the root directory:

```bash
./gradlew build
```
This will assemble the JAR files for all modules.

## How to Run

### 1. Database Setup
The project is configured to use a MariaDB/MySQL database.
1.  Create a database named `cmms`.
2.  The application will automatically create and seed the necessary tables using Flyway when it starts for the first time. The connection details are in `cmms10/src/main/resources/application.yml`.

### 2. Running the `cmms10` Application
To run the CMMS application, use the following Gradle command:

```bash
./gradlew :cmms10:bootRun
```
The application will start on port **9010**.

## User and Administrator Setup

### Default Administrator
A default administrator account is created automatically by the database migration script (`common-security/src/main/resources/db/migration/V1__platform_core.sql`).
-   **Username**: `admin`
-   **Password**: `password`

### Registering New Users/Admins
The architectural design intends for the `platform10` module to serve as a graphical user interface for managing companies, departments, users, and roles.

Since `platform10` has not yet been implemented, new users must be added directly to the database tables (`company`, `dept`, `user_account`, `role`, `user_role`).

## Application Usage Flow

1.  **Access the Application**: Open your web browser and navigate to `http://localhost:9010`.
2.  **Login**: Because you are not authenticated, the application will automatically redirect you to the login page at `http://localhost:9010/login`.
3.  **Enter Credentials**: Use the default administrator credentials (`admin`/`password`) to log in.
4.  **Post-Login**: After a successful login, you will be redirected to the main application page. For `cmms10`, this will likely be the work permits list at `http://localhost:9010/work-permits`.
5.  **Use the Application**: From the work permits page, you can:
    -   View a list of existing work permits.
    -   Click "New Work Permit" to go to the creation form.
    -   On the form, you can fill in the details and select a "Permit Type" and a "Department" from the dropdown menus.
    -   Save the form to create a new work permit.
