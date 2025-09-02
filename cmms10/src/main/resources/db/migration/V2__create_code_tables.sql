/**
 * Flyway migration script for creating the code_type and code tables.
 */

-- Create code_type table to hold categories of codes
CREATE TABLE code_type (
    company_id BIGINT NOT NULL,
    code_type VARCHAR(50) NOT NULL,
    code_type_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (company_id, code_type)
);

-- Create code table to hold individual codes
CREATE TABLE code (
    company_id BIGINT NOT NULL,
    code_id VARCHAR(50) NOT NULL,
    code_name VARCHAR(255) NOT NULL,
    code_type VARCHAR(50) NOT NULL,
    sort_order INT DEFAULT 0,
    PRIMARY KEY (company_id, code_id),
    CONSTRAINT fk_code_to_code_type FOREIGN KEY (company_id, code_type) REFERENCES code_type (company_id, code_type)
);

-- Add some sample data for permit types
INSERT INTO code_type (company_id, code_type, code_type_name) VALUES (1, 'PERMIT_TYPE', 'Work Permit Types');
INSERT INTO code (company_id, code_id, code_name, code_type, sort_order) VALUES (1, 'HOT_WORK', 'Hot Work Permit', 'PERMIT_TYPE', 1);
INSERT INTO code (company_id, code_id, code_name, code_type, sort_order) VALUES (1, 'COLD_WORK', 'Cold Work Permit', 'PERMIT_TYPE', 2);
INSERT INTO code (company_id, code_id, code_name, code_type, sort_order) VALUES (1, 'CONFINED_SPACE', 'Confined Space Entry Permit', 'PERMIT_TYPE', 3);
INSERT INTO code (company_id, code_id, code_name, code_type, sort_order) VALUES (1, 'ELECTRICAL', 'Electrical Work Permit', 'PERMIT_TYPE', 4);
