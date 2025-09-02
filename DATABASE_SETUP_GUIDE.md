# Database Setup Guide for Platform

This guide provides instructions for setting up the initial database schema required for the platform applications.

## 1. Database and Schema Creation

It is recommended to use a MariaDB database. First, create the schemas for the common entities and the business domains. The `project-archtecture.txt` recommends a common `platform` schema and separate schemas for each business module (e.g., `cmms`, `wflow`).

```sql
CREATE DATABASE platform CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE cmms CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE wflow CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE market CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE mportal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## 2. Common Schema (`platform`) Table Creation

The following SQL script creates the core tables for managing companies, users, and roles within the `platform` schema. This is based on the `V1__platform_core.sql` mentioned in the architecture document.

**Note:** It is highly recommended to use a database migration tool like Flyway, and place these scripts in `common-security/src/main/resources/db/migration`.

```sql
-- Use the 'platform' schema
USE platform;

-- Table for Companies
CREATE TABLE company (
    company_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table for Sites (belongs to a Company)
CREATE TABLE site (
    company_id BIGINT NOT NULL,
    site_id BIGINT AUTO_INCREMENT,
    site_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, site_id),
    FOREIGN KEY (company_id) REFERENCES company(company_id) ON DELETE CASCADE
);

-- Table for Departments (belongs to a Site)
CREATE TABLE dept (
    company_id BIGINT NOT NULL,
    site_id BIGINT NOT NULL,
    dept_id BIGINT AUTO_INCREMENT,
    dept_name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, site_id, dept_id),
    FOREIGN KEY (company_id, site_id) REFERENCES site(company_id, site_id) ON DELETE CASCADE
);

-- Table for User Accounts
CREATE TABLE user_account (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL, -- Should be a BCrypt hash
    email VARCHAR(255) UNIQUE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table for Roles
CREATE TABLE role (
    role_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(100) NOT NULL UNIQUE -- e.g., 'ROLE_PLATFORM_ADMIN', 'ROLE_COMPANY_ADMIN', 'ROLE_USER'
);

-- Join Table for User-Role relationship
CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    company_id BIGINT, -- Can be NULL for platform-wide roles
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user_account(user_id),
    FOREIGN KEY (role_id) REFERENCES role(role_id),
    FOREIGN KEY (company_id) REFERENCES company(company_id)
);

```

## 3. Initial Data Seeding (Bootstrap)

After creating the tables, you need to insert some initial data for roles and a platform administrator.

```sql
-- Insert base roles
INSERT INTO role (role_name) VALUES ('ROLE_PLATFORM_ADMIN'), ('ROLE_COMPANY_ADMIN'), ('ROLE_USER');

-- Insert a sample company
INSERT INTO company (company_name) VALUES ('Default Company');

-- Create a platform administrator user
-- The password 'admin' is shown here for demonstration.
-- You should generate a BCrypt hash for your password.
-- Example hash for 'admin': $2a$10$... (generate a real one)
INSERT INTO user_account (username, password, email, is_active) VALUES ('admin', '$2a$10$8.A..3.Cq.j8s/4z0g.vV.vV.vV.vV.vV.vV.vV.vV.vV.vV.vV.vV', 'admin@platform.com', TRUE);

-- Assign the PLATFORM_ADMIN role to the admin user
-- Assuming user_id=1 and role_id=1 from the inserts above.
INSERT INTO user_role (user_id, role_id, company_id) VALUES (1, 1, NULL);

```

## 4. Business Schema (e.g., `cmms`)

For each business module, create tables within their respective schemas. As per the rules, all tables should have a composite primary key including `company_id`.

Example for `cmms.work_permit`:
```sql
USE cmms;

CREATE TABLE work_permit (
    company_id BIGINT NOT NULL,
    permit_id BIGINT AUTO_INCREMENT,
    permit_name VARCHAR(255) NOT NULL,
    -- other fields...
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, permit_id)
    -- Cannot add a direct FK to platform.company as schemas are separate,
    -- this link is enforced by the application logic.
);
```
This completes the basic database setup. Remember to manage future changes through a migration tool.
