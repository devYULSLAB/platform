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

## Build and Run Instructions

This project uses a Gradle Wrapper, so you don't need to install Gradle on your system. The wrapper script (`./gradlew`) will download and use the correct Gradle version automatically.

### Wrapper Maintenance
If you need to update the Gradle version used by the project, you can run the following command. This requires a local Gradle installation.
```bash
# Example: Update to Gradle 8.14
gradle wrapper --gradle-version 8.14
```
After running the command, be sure to commit the changed wrapper files to version control.

### Prerequisites
- Java 24 JDK

### Build Order
This is a multi-module project. The build order is determined by the dependencies between modules:
1.  `common-*` modules (libraries) are built first.
2.  Application modules (`cmms10`, etc.) that depend on the common modules are built next.

You do not need to manage this order manually. Running a Gradle task on an application module will automatically build its dependencies first.

### Building the Application
You can build the entire project or a specific application module.

-   **Build All Modules**: To build all modules and run all tests, execute the following from the root directory:
    ```bash
    ./gradlew build
    ```
-   **Build a Specific Module**: To build only the `cmms10` application and its dependencies:
    ```bash
    ./gradlew :cmms10:build
    ```
This will assemble the JAR file for the specified module in its `build/libs` directory.

### 1. Database Setup
The project is configured to use a MariaDB/MySQL database.
1.  Create a database named `cmms`.
2.  The application will automatically create and seed the necessary tables using Flyway when it starts for the first time. The connection details are in `cmms10/src/main/resources/application.yml`.

### 2. Choosing an Application to Run
You cannot run the "entire project" at once. You must choose and run a specific application module (e.g., `cmms10`). Each runnable module is a separate Spring Boot application.

To run the `cmms10` application, use the following Gradle command:
```bash
./gradlew :cmms10:bootRun
```
The application will start on port **9010**.

## Configuration and Profiles

### Configuration File
Application settings (like database connections) are managed in the `application.yml` file located in each application module's `src/main/resources` directory (e.g., `cmms10/src/main/resources/application.yml`).

### Profiles
The project is not pre-configured with multiple profiles (e.g., `dev`, `test`, `prod`). It runs using the default settings defined directly in `application.yml`.

To use profiles, you would need to create profile-specific configuration files (e.g., `application-dev.yml`) and activate the desired profile using a Spring Boot property.

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
