-- V1__platform_core.sql

-- Company table
CREATE TABLE company (
    company_id BIGINT NOT NULL AUTO_INCREMENT,
    company_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (company_id)
);

-- Site table (a company can have multiple sites)
CREATE TABLE site (
    company_id BIGINT NOT NULL,
    site_id BIGINT NOT NULL AUTO_INCREMENT,
    site_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (company_id, site_id),
    CONSTRAINT fk_site_to_company FOREIGN KEY (company_id) REFERENCES company(company_id)
);

-- Department table
CREATE TABLE dept (
    company_id BIGINT NOT NULL,
    dept_id BIGINT NOT NULL AUTO_INCREMENT,
    dept_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (company_id, dept_id),
    CONSTRAINT fk_dept_to_company FOREIGN KEY (company_id) REFERENCES company(company_id)
);

-- User account table
CREATE TABLE user_account (
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    is_active BOOLEAN DEFAULT TRUE,
    PRIMARY KEY (user_id)
);

-- Role table
CREATE TABLE role (
    role_id BIGINT NOT NULL AUTO_INCREMENT,
    role_name VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (role_id)
);

-- User-Role mapping table, linking users to roles within a specific company
CREATE TABLE user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    company_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id, company_id),
    CONSTRAINT fk_user_role_to_user FOREIGN KEY (user_id) REFERENCES user_account(user_id),
    CONSTRAINT fk_user_role_to_role FOREIGN KEY (role_id) REFERENCES role(role_id),
    CONSTRAINT fk_user_role_to_company FOREIGN KEY (company_id) REFERENCES company(company_id)
);

-- ### SEED DATA ###

-- Default Company
INSERT INTO company (company_id, company_name) VALUES (1, 'Default Company');

-- Default Departments for Company 1
INSERT INTO dept (company_id, dept_id, dept_name) VALUES (1, 1, 'Maintenance');
INSERT INTO dept (company_id, dept_id, dept_name) VALUES (1, 2, 'Production');
INSERT INTO dept (company_id, dept_id, dept_name) VALUES (1, 3, 'IT');

-- Default Roles
INSERT INTO role (role_id, role_name) VALUES (1, 'ROLE_USER');
INSERT INTO role (role_id, role_name) VALUES (2, 'ROLE_COMPANY_ADMIN');
INSERT INTO role (role_id, role_name) VALUES (3, 'ROLE_PLATFORM_ADMIN');

-- Default Admin User (password is 'password' bcrypt-encoded)
INSERT INTO user_account (user_id, username, password, email, is_active)
VALUES (1, 'admin', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqR2e5RzTTN2NVBLOqr4i/g.xGz6', 'admin@platform.com', TRUE);

-- Assign Admin all roles for the default company
INSERT INTO user_role (user_id, role_id, company_id) VALUES (1, 1, 1); -- ROLE_USER
INSERT INTO user_role (user_id, role_id, company_id) VALUES (1, 2, 1); -- ROLE_COMPANY_ADMIN
INSERT INTO user_role (user_id, role_id, company_id) VALUES (1, 3, 1); -- ROLE_PLATFORM_ADMIN
