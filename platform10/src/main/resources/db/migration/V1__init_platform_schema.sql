-- 1) Company
CREATE TABLE IF NOT EXISTS company (
    company_id   CHAR(5)       NOT NULL,
    company_name VARCHAR(100)  NOT NULL,
    is_active    BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id)
);

-- 2) Site (회사 소속)
CREATE TABLE IF NOT EXISTS site (
    company_id   CHAR(5)       NOT NULL,
    site_id      CHAR(5)       NOT NULL,
    site_name    VARCHAR(100)  NOT NULL,
    is_active    BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, site_id),
    CONSTRAINT fk_site_company
        FOREIGN KEY (company_id)
        REFERENCES company(company_id)
        ON DELETE CASCADE
);

-- 3) Dept (사이트 소속, 자기참조 허용)
CREATE TABLE IF NOT EXISTS dept (
    company_id     CHAR(5)       NOT NULL,
    site_id        CHAR(5)       NOT NULL,
    dept_id        CHAR(5)       NOT NULL,
    dept_name      VARCHAR(100)  NOT NULL,
    parent_dept_id CHAR(5)       NULL,
    dept_level     INT           NULL,
    is_active      BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, site_id, dept_id),
    CONSTRAINT fk_dept_site
        FOREIGN KEY (company_id, site_id)
        REFERENCES site(company_id, site_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_dept_parent
        FOREIGN KEY (company_id, site_id, parent_dept_id)
        REFERENCES dept(company_id, site_id, dept_id)
        ON DELETE SET NULL
);

-- 4) User Account (회사 필수, 사이트/부서 옵션)
CREATE TABLE IF NOT EXISTS user_account (
    company_id CHAR(5)       NOT NULL,
    user_id    CHAR(5)       NOT NULL,
    username   VARCHAR(100)  NOT NULL,
    password   VARCHAR(255)  NOT NULL,
    site_id    CHAR(5)       NULL,
    dept_id    CHAR(5)       NULL,
    phone      VARCHAR(100)  NULL,
    email      VARCHAR(100)  NULL,
    is_active  BOOLEAN       NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, user_id),
    CONSTRAINT uq_user_username_per_company UNIQUE (company_id, username),
    CONSTRAINT fk_user_company
        FOREIGN KEY (company_id)
        REFERENCES company(company_id),
    CONSTRAINT fk_user_site
        FOREIGN KEY (company_id, site_id)
        REFERENCES site(company_id, site_id)
        ON DELETE SET NULL,
    CONSTRAINT fk_user_dept
        FOREIGN KEY (company_id, site_id, dept_id)
        REFERENCES dept(company_id, site_id, dept_id)
        ON DELETE SET NULL
);

-- 5) Role (회사별 롤 사전)
CREATE TABLE IF NOT EXISTS role (
    company_id CHAR(5)       NOT NULL,
    role_id    CHAR(5)       NOT NULL,
    role_name  VARCHAR(100)  NOT NULL,
    created_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, role_id),
    CONSTRAINT uq_role_name_per_company UNIQUE (company_id, role_name),
    CONSTRAINT fk_role_company
        FOREIGN KEY (company_id)
        REFERENCES company(company_id)
);

-- 6) User-Role (다대다 매핑)
CREATE TABLE IF NOT EXISTS user_role (
    company_id CHAR(5)   NOT NULL,
    user_id    CHAR(5)   NOT NULL,
    role_id    CHAR(5)   NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (company_id, user_id, role_id),
    CONSTRAINT fk_ur_user
        FOREIGN KEY (company_id, user_id)
        REFERENCES user_account(company_id, user_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_ur_role
        FOREIGN KEY (company_id, role_id)
        REFERENCES role(company_id, role_id)
        ON DELETE CASCADE
);
