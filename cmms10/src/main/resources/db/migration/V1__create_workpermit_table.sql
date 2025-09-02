/**
 * Flyway migration script for creating the work_permit table.
 */
CREATE TABLE work_permit (
    company_id BIGINT NOT NULL,
    permit_id BIGINT NOT NULL AUTO_INCREMENT,
    permit_name VARCHAR(255) NOT NULL,
    requestor_name VARCHAR(255) NOT NULL,
    status VARCHAR(50),
    permit_type_code VARCHAR(255),
    created_by VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255),
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, permit_id)
);

-- Note: AUTO_INCREMENT on a composite key's second part is a MySQL/MariaDB feature.
-- This allows permit_id to be unique within a company.
-- A sequence might be needed for other databases.

-- Add an index for faster lookups by company
CREATE INDEX idx_wp_company_id ON work_permit (company_id);
