USE platform;



-- 1) Company

CREATE TABLE IF NOT EXISTS company (

&nbsp;   company\_id   CHAR(5)       NOT NULL,

&nbsp;   company\_name VARCHAR(100)  NOT NULL,

&nbsp;   is\_active    BOOLEAN       NOT NULL DEFAULT TRUE,

&nbsp;   created\_at   TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP,

&nbsp;   updated\_at   TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

&nbsp;   PRIMARY KEY (company\_id)

);



-- 2) Site (회사 소속)

CREATE TABLE IF NOT EXISTS site (

&nbsp;   company\_id   CHAR(5)       NOT NULL,

&nbsp;   site\_id      CHAR(5)       NOT NULL,

&nbsp;   site\_name    VARCHAR(100)  NOT NULL,

&nbsp;   is\_active    BOOLEAN       NOT NULL DEFAULT TRUE,

&nbsp;   created\_at   TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP,

&nbsp;   updated\_at   TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

&nbsp;   PRIMARY KEY (company\_id, site\_id),

&nbsp;   CONSTRAINT fk\_site\_company

&nbsp;       FOREIGN KEY (company\_id)

&nbsp;       REFERENCES company(company\_id)

&nbsp;       ON DELETE CASCADE

);



-- 3) Dept (사이트 소속, 자기참조 허용)

CREATE TABLE IF NOT EXISTS dept (

&nbsp;   company\_id     CHAR(5)       NOT NULL,

&nbsp;   site\_id        CHAR(5)       NOT NULL,

&nbsp;   dept\_id        CHAR(5)       NOT NULL,

&nbsp;   dept\_name      VARCHAR(100)  NOT NULL,

&nbsp;   parent\_dept\_id CHAR(5)       NULL,

&nbsp;   dept\_level     INT           NULL,

&nbsp;   is\_active      BOOLEAN       NOT NULL DEFAULT TRUE,

&nbsp;   created\_at     TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP,

&nbsp;   updated\_at     TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

&nbsp;   PRIMARY KEY (company\_id, site\_id, dept\_id),

&nbsp;   CONSTRAINT fk\_dept\_site

&nbsp;       FOREIGN KEY (company\_id, site\_id)

&nbsp;       REFERENCES site(company\_id, site\_id)

&nbsp;       ON DELETE CASCADE,

&nbsp;   CONSTRAINT fk\_dept\_parent

&nbsp;       FOREIGN KEY (company\_id, site\_id, parent\_dept\_id)

&nbsp;       REFERENCES dept(company\_id, site\_id, dept\_id)

&nbsp;       ON DELETE SET NULL

);



-- 4) User Account (회사 필수, 사이트/부서 옵션)

CREATE TABLE IF NOT EXISTS user\_account (

&nbsp;   company\_id CHAR(5)       NOT NULL,

&nbsp;   user\_id    CHAR(5)       NOT NULL,

&nbsp;   username   VARCHAR(100)  NOT NULL,

&nbsp;   password   VARCHAR(255)  NOT NULL, -- BCrypt hash

&nbsp;   site\_id    CHAR(5)       NULL,     -- 회사 전사 계정일 수 있으므로 NULL 허용

&nbsp;   dept\_id    CHAR(5)       NULL,

&nbsp;   phone      VARCHAR(100)  NULL,

&nbsp;   email      VARCHAR(100)  NULL,

&nbsp;   is\_active  BOOLEAN       NOT NULL DEFAULT TRUE,

&nbsp;   created\_at TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP,

&nbsp;   updated\_at TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

&nbsp;   PRIMARY KEY (company\_id, user\_id),

&nbsp;   CONSTRAINT uq\_user\_username\_per\_company UNIQUE (company\_id, username),

&nbsp;   CONSTRAINT fk\_user\_company

&nbsp;       FOREIGN KEY (company\_id)

&nbsp;       REFERENCES company(company\_id),

&nbsp;   CONSTRAINT fk\_user\_site

&nbsp;       FOREIGN KEY (company\_id, site\_id)

&nbsp;       REFERENCES site(company\_id, site\_id)

&nbsp;       ON DELETE SET NULL,

&nbsp;   CONSTRAINT fk\_user\_dept

&nbsp;       FOREIGN KEY (company\_id, site\_id, dept\_id)

&nbsp;       REFERENCES dept(company\_id, site\_id, dept\_id)

&nbsp;       ON DELETE SET NULL

);



-- 5) Role (회사별 롤 사전)

CREATE TABLE IF NOT EXISTS role (

&nbsp;   company\_id CHAR(5)       NOT NULL,

&nbsp;   role\_id    CHAR(5)       NOT NULL,

&nbsp;   role\_name  VARCHAR(100)  NOT NULL, -- 예: ROLE\_PLATFORM\_ADMIN / ROLE\_COMPANY\_ADMIN / ROLE\_USER

&nbsp;   created\_at TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP,

&nbsp;   updated\_at TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

&nbsp;   PRIMARY KEY (company\_id, role\_id),

&nbsp;   CONSTRAINT uq\_role\_name\_per\_company UNIQUE (company\_id, role\_name),

&nbsp;   CONSTRAINT fk\_role\_company

&nbsp;       FOREIGN KEY (company\_id)

&nbsp;       REFERENCES company(company\_id)

);



-- 6) User-Role (다대다 매핑)

CREATE TABLE IF NOT EXISTS user\_role (

&nbsp;   company\_id CHAR(5)   NOT NULL,

&nbsp;   user\_id    CHAR(5)   NOT NULL,

&nbsp;   role\_id    CHAR(5)   NOT NULL,

&nbsp;   created\_at TIMESTAMP NOT NULL DEFAULT CURRENT\_TIMESTAMP,

&nbsp;   PRIMARY KEY (company\_id, user\_id, role\_id),

&nbsp;   CONSTRAINT fk\_ur\_user

&nbsp;       FOREIGN KEY (company\_id, user\_id)

&nbsp;       REFERENCES user\_account(company\_id, user\_id)

&nbsp;       ON DELETE CASCADE,

&nbsp;   CONSTRAINT fk\_ur\_role

&nbsp;       FOREIGN KEY (company\_id, role\_id)

&nbsp;       REFERENCES role(company\_id, role\_id)

&nbsp;       ON DELETE CASCADE

);



USE platform;



-- 회사

INSERT INTO company (company\_id, company\_name, is\_active)

VALUES ('C0001', '샘플회사', TRUE);



-- 사이트

INSERT INTO site (company\_id, site\_id, site\_name, is\_active)

VALUES 

&nbsp;('C0001', 'SITE1', '본사', TRUE),

&nbsp;('C0001', 'SITE2', '지사', TRUE);



-- 부서

INSERT INTO dept (company\_id, site\_id, dept\_id, dept\_name, is\_active)

VALUES 

&nbsp;('C0001', 'SITE1', 'DEPT1', '관리부', TRUE),

&nbsp;('C0001', 'SITE2', 'DEPT2', '운영부', TRUE);



-- 사용자 (패스워드 원문: 1234)

INSERT INTO user\_account (company\_id, user\_id, username, password, site\_id, dept\_id, is\_active)

VALUES

&nbsp;('C0001', 'U0001', 'super',  '$2a$10$Dow1kxW0Jvlm3ZHjV5T3f.0VxO31UgMa8l0kS0lfoLtXuhxzlmXRO', NULL,   NULL,   TRUE),  -- password: 1234

&nbsp;('C0001', 'U0002', 'admin',  '$2a$10$Dow1kxW0Jvlm3ZHjV5T3f.0VxO31UgMa8l0kS0lfoLtXuhxzlmXRO', 'SITE1','DEPT1', TRUE),  -- password: 1234

&nbsp;('C0001', 'U0003', 'user1',  '$2a$10$Dow1kxW0Jvlm3ZHjV5T3f.0VxO31UgMa8l0kS0lfoLtXuhxzlmXRO', 'SITE1','DEPT1', TRUE),  -- password: 1234

&nbsp;('C0001', 'U0004', 'user2',  '$2a$10$Dow1kxW0Jvlm3ZHjV5T3f.0VxO31UgMa8l0kS0lfoLtXuhxzlmXRO', 'SITE2','DEPT2', TRUE);  -- password: 1234



-- 롤

INSERT INTO role (company\_id, role\_id, role\_name)

VALUES

&nbsp;('C0001', 'RPLAT', 'ROLE\_PLATFORM\_ADMIN'),

&nbsp;('C0001', 'RCOMP', 'ROLE\_COMPANY\_ADMIN'),

&nbsp;('C0001', 'RMGMR', 'ROLE\_MANAGER'),

&nbsp;('C0001', 'R0001', 'ROLE\_USER1'),

&nbsp;('C0001', 'R0002', 'ROLE\_USER2');



-- 사용자-롤 매핑

INSERT INTO user\_role (company\_id, user\_id, role\_id)

VALUES

&nbsp;('C0001', 'U0001', 'RPLAT'), -- super → ROLE\_PLATFORM

&nbsp;('C0001', 'U0002', 'RCOMP'), -- admin → ROLE\_COMPANY\_ADMIN

&nbsp;('C0001', 'U0003', 'R0001'), -- user1 → ROLE\_USER1

&nbsp;('C0001', 'U0004', 'R0002'); -- user2 → ROLE\_USER2





