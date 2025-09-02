-- Company
INSERT INTO company (company_id, company_name, is_active)
VALUES ('C0001', '샘플회사', TRUE);

-- Site
INSERT INTO site (company_id, site_id, site_name, is_active)
VALUES
('C0001', 'SITE1', '본사', TRUE),
('C0001', 'SITE2', '지사', TRUE);

-- Dept
INSERT INTO dept (company_id, site_id, dept_id, dept_name, is_active)
VALUES
('C0001', 'SITE1', 'DEPT1', '관리부', TRUE),
('C0001', 'SITE2', 'DEPT2', '운영부', TRUE);

-- User Account (password: 1234)
INSERT INTO user_account (company_id, user_id, username, password, site_id, dept_id, is_active)
VALUES
('C0001', 'U0001', 'super',  '$2a$10$Dow1kxW0Jvlm3ZHjV5T3f.0VxO31UgMa8l0kS0lfoLtXuhxzlmXRO', NULL,   NULL,   TRUE),
('C0001', 'U0002', 'admin',  '$2a$10$Dow1kxW0Jvlm3ZHjV5T3f.0VxO31UgMa8l0kS0lfoLtXuhxzlmXRO', 'SITE1','DEPT1', TRUE),
('C0001', 'U0003', 'user1',  '$2a$10$Dow1kxW0Jvlm3ZHjV5T3f.0VxO31UgMa8l0kS0lfoLtXuhxzlmXRO', 'SITE1','DEPT1', TRUE),
('C0001', 'U0004', 'user2',  '$2a$10$Dow1kxW0Jvlm3ZHjV5T3f.0VxO31UgMa8l0kS0lfoLtXuhxzlmXRO', 'SITE2','DEPT2', TRUE);

-- Role
INSERT INTO role (company_id, role_id, role_name)
VALUES
('C0001', 'RPLAT', 'ROLE_PLATFORM_ADMIN'),
('C0001', 'RCOMP', 'ROLE_COMPANY_ADMIN'),
('C0001', 'RMGMR', 'ROLE_MANAGER'),
('C0001', 'R0001', 'ROLE_USER1'),
('C0001', 'R0002', 'ROLE_USER2');

-- User-Role Mapping
INSERT INTO user_role (company_id, user_id, role_id)
VALUES
('C0001', 'U0001', 'RPLAT'),
('C0001', 'U0002', 'RCOMP'),
('C0001', 'U0003', 'R0001'),
('C0001', 'U0004', 'R0002');
