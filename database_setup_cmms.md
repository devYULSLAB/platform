/\*\*

 \* 프로그램명: CMMS 코어 스키마 초기화

 \* 기능: 코드/설비/자재/재고/이력/점검/작업지시/작업허가 테이블 정의

 \* 생성자: devYULSLAB

 \* 생성일: 2025-02-28

 \* 변경일: 2025-09-02

 \*/



-- (존재 가정) CREATE DATABASE cmms CHARACTER SET utf8mb4 COLLATE utf8mb4\_unicode\_ci;

USE cmms;



-- =========================

-- 코드 타입/코드 (코드성 값 CHAR(5))

-- =========================

CREATE TABLE code\_type (

    company\_id  CHAR(5)       NOT NULL,

    code\_type   VARCHAR(20)   NOT NULL,      -- 예: 'ASSET','DEPRE','JOBTP','MOVT' 등

    type\_name   VARCHAR(100)  NOT NULL,

    sort\_no     INT           NULL,

    is\_active   BOOLEAN       NOT NULL DEFAULT TRUE,

    created\_at  TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    updated\_at  TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, code\_type)

);



CREATE TABLE code (

    company\_id  CHAR(5)       NOT NULL,

    code\_type   VARCHAR(20)   NOT NULL,

    code\_id     CHAR(5)       NOT NULL,      -- 코드 값은 CHAR(5)

    code\_name   VARCHAR(100)  NOT NULL,

    sort\_no     INT           NULL,

    is\_active   BOOLEAN       NOT NULL DEFAULT TRUE,

    created\_at  TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    updated\_at  TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, code\_type, code\_id),

    CONSTRAINT fk\_code\_type

        FOREIGN KEY (company\_id, code\_type)

        REFERENCES code\_type(company\_id, code\_type)

);



-- =========================

-- 기능 위치 (옵션)

-- =========================

CREATE TABLE func\_master (

    company\_id     CHAR(5)      NOT NULL,

    func\_id        CHAR(5)      NOT NULL,

    func\_name      VARCHAR(100) NOT NULL,

    parent\_func\_id CHAR(5)      NULL,

    created\_at     TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    updated\_at     TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, func\_id),

    CONSTRAINT fk\_func\_parent

        FOREIGN KEY (company\_id, parent\_func\_id)

        REFERENCES func\_master(company\_id, func\_id)

        ON DELETE SET NULL

);



-- =========================

-- 설비 마스터 (plant\_master)

-- =========================

CREATE TABLE plant\_master (

    company\_id        CHAR(5)      NOT NULL,

    plant\_id          CHAR(10)     NOT NULL,

    site\_id           CHAR(5)      NOT NULL,    -- 설치 사이트

    plant\_name        VARCHAR(100) NOT NULL,

    plant\_loc         VARCHAR(100) NULL,        -- 설치 위치(텍스트)

    dept\_id           CHAR(5)      NULL,        -- 관리 부서 (platform.dept)

    func\_id           CHAR(5)      NULL,        -- 기능 위치(옵션)

    -- 자산 타입(코드): code\_type='ASSET'

    master\_code\_type  VARCHAR(20)  NULL DEFAULT 'ASSET',

    master\_code\_id    CHAR(5)      NULL,

    -- 제조사 정보

    maker\_name        VARCHAR(100) NULL,

    spec              VARCHAR(100) NULL,

    model             VARCHAR(100) NULL,

    serial\_no         VARCHAR(100) NULL,

    -- 자산 정보

    acq\_cost          DECIMAL(18,2) NULL,       -- 취득가

    residual\_value    DECIMAL(18,2) NULL,       -- 잔존가

    -- 상각 방법(코드): code\_type='DEPRE'

    depr\_code\_type    VARCHAR(20)  NULL DEFAULT 'DEPRE',

    depr\_code\_id      CHAR(5)      NULL,

    -- 운영 플래그

    is\_operating          CHAR(1)  NULL,        -- Y/N

    is\_pm\_target          CHAR(1)  NULL,        -- 예방점검 대상(Y/N)

    is\_psm\_target         CHAR(1)  NULL,        -- PSM 대상(Y/N)

    is\_workpermit\_target  CHAR(1)  NULL,        -- 작업허가 대상(Y/N)

    notes             VARCHAR(1000) NULL,

    file\_group\_id     CHAR(10)     NULL,

    created\_at        TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    updated\_at        TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, plant\_id),

    -- 교차 스키마 FK: platform.site, platform.dept

    CONSTRAINT fk\_plant\_site

        FOREIGN KEY (company\_id, site\_id)

        REFERENCES platform.site(company\_id, site\_id),

    CONSTRAINT fk\_plant\_dept

        FOREIGN KEY (company\_id, site\_id, dept\_id)

        REFERENCES platform.dept(company\_id, site\_id, dept\_id)

        ON DELETE SET NULL,

    CONSTRAINT fk\_plant\_func

        FOREIGN KEY (company\_id, func\_id)

        REFERENCES func\_master(company\_id, func\_id)

        ON DELETE SET NULL,

    CONSTRAINT fk\_plant\_asset\_code

        FOREIGN KEY (company\_id, master\_code\_type, master\_code\_id)

        REFERENCES code(company\_id, code\_type, code\_id)

        ON DELETE SET NULL,

    CONSTRAINT fk\_plant\_depre\_code

        FOREIGN KEY (company\_id, depr\_code\_type, depr\_code\_id)

        REFERENCES code(company\_id, code\_type, code\_id)

        ON DELETE SET NULL

);



-- =========================

-- 자재 마스터 (inventory\_master)

-- =========================

 CREATE TABLE inventory\_master (

      company\_id        CHAR(5)      NOT NULL,

      inventory\_id      CHAR(10)     NOT NULL,

      inventory\_name         VARCHAR(100) NOT NULL,

      dept\_id           CHAR(5)      NULL,       -- 관리부서

      master\_code\_type  VARCHAR(20)  NULL DEFAULT 'ASSET',

      master\_code\_id    CHAR(5)      NULL,

      maker\_name        VARCHAR(100) NULL,

      spec              VARCHAR(100) NULL,

      model             VARCHAR(100) NULL,

      serial\_no         VARCHAR(100) NULL,

      notes             VARCHAR(100) NULL,

      file\_group\_id     CHAR(10)     NULL,

      created\_at        TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP,

      updated\_at        TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

     PRIMARY KEY (company\_id, inventory\_id),

     CONSTRAINT fk\_inv\_dept

         FOREIGN KEY (company\_id, dept\_id)

         REFERENCES platform.dept(company\_id, dept\_id)

         ON DELETE SET NULL,

      CONSTRAINT fk\_inv\_asset\_code

          FOREIGN KEY (company\_id, master\_code\_type, master\_code\_id)

          REFERENCES code(company\_id, code\_type, code\_id)

          ON DELETE SET NULL

 );

-- 위치 마스터(이미 존재한다면 재활용)

CREATE TABLE loc\_master (

    company\_id    CHAR(5)      NOT NULL,

    site\_id           CHAR(5)      NOT NULL,

    loc\_id        CHAR(10)     NOT NULL,

    loc\_name      VARCHAR(100) NOT NULL,

    parent\_loc\_id CHAR(10)     NULL,

    created\_at    TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    updated\_at    TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, site\_id, loc\_id),

    CONSTRAINT fk\_loc\_parent

        FOREIGN KEY (company\_id, parent\_loc\_id)

        REFERENCES loc\_master(company\_id, loc\_id)

        ON DELETE SET NULL

);



-- =========================

-- 재고 (site/loc 단위)

-- =========================

CREATE TABLE inventory\_stock (

    company\_id     CHAR(5)      NOT NULL,

    site\_id        CHAR(5)      NOT NULL,

    loc\_id         CHAR(10)     NOT NULL,

    inventory\_id   CHAR(10)     NOT NULL,

    qty            DECIMAL(18,4) NOT NULL DEFAULT 0,

    avg\_unit\_cost  DECIMAL(18,4) NOT NULL DEFAULT 0,  -- 이동평균법 등

    amount         DECIMAL(18,2) NOT NULL DEFAULT 0,  -- qty \* avg\_unit\_cost

    updated\_at     TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, site\_id, loc\_id, inventory\_id),

    CONSTRAINT fk\_stock\_site

        FOREIGN KEY (company\_id, site\_id)

        REFERENCES platform.site(company\_id, site\_id),

    CONSTRAINT fk\_stock\_loc

        FOREIGN KEY (company\_id, loc\_id)

        REFERENCES loc\_master(company\_id, loc\_id),

    CONSTRAINT fk\_stock\_item

        FOREIGN KEY (company\_id, inventory\_id)

        REFERENCES inventory\_master(company\_id, inventory\_id)

);



-- =========================

-- 재고 이력 (입/출고)

-- =========================

CREATE TABLE inventory\_history (

    company\_id     CHAR(5)      NOT NULL,

    tx\_id          CHAR(10)     NOT NULL,          -- 거래 ID

    site\_id        CHAR(5)      NOT NULL,

    loc\_id         CHAR(10)     NOT NULL,

    inventory\_id   CHAR(10)     NOT NULL,

    movement\_date  DATETIME     NOT NULL,

    -- 이동유형 코드: code\_type='MOVT' (예: IN, OUT, ADJ 등)

    move\_code\_type VARCHAR(20)  NULL DEFAULT 'MOVT',

    move\_code\_id   CHAR(5)      NULL,

    qty\_in         DECIMAL(18,4) NOT NULL DEFAULT 0,

    qty\_out        DECIMAL(18,4) NOT NULL DEFAULT 0,

    unit\_cost      DECIMAL(18,4) NULL,

    amount         DECIMAL(18,2) NULL,

    ref\_type       VARCHAR(20)  NULL,              -- 참조(예: 'PO','WO' 등)

    ref\_id         CHAR(10)     NULL,

    notes           VARCHAR(100) NULL,

    created\_at     TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, tx\_id),

    CONSTRAINT fk\_hist\_site  FOREIGN KEY (company\_id, site\_id) REFERENCES platform.site(company\_id, site\_id),

    CONSTRAINT fk\_hist\_loc   FOREIGN KEY (company\_id, loc\_id)  REFERENCES loc\_master(company\_id, loc\_id),

    CONSTRAINT fk\_hist\_item  FOREIGN KEY (company\_id, inventory\_id) REFERENCES inventory\_master(company\_id, inventory\_id),

    CONSTRAINT fk\_hist\_movt  FOREIGN KEY (company\_id, move\_code\_type, move\_code\_id) REFERENCES code(company\_id, code\_type, code\_id) ON DELETE SET NULL

);



-- =========================

-- 점검 (Inspection) - 스케줄 테이블 삭제, 단일 엔티티로 통합

-- =========================

CREATE TABLE inspection (

    company\_id       CHAR(5)      NOT NULL,

    inspection\_id    CHAR(10)     NOT NULL,

    title            VARCHAR(100) NOT NULL,

    plant\_id         CHAR(10)     NULL,            -- 대표 설비(옵션)

    dept\_id          CHAR(5)      NULL,            -- 수행 부서

    -- 작업유형 코드: code\_type='JOBTP'

    job\_code\_type    VARCHAR(20)  NULL DEFAULT 'JOBTP',

    job\_code\_id      CHAR(5)      NULL,

    planned\_date     DATE         NULL,            -- 계획일

    performed\_date   DATE         NULL,            -- 수행일

    -- 결재 상태(계획/수행) 예: DRAFT/IN\_PROGRESS/DONE 등

    plan\_status      VARCHAR(20)  NULL,

    perform\_status   VARCHAR(20)  NULL,

    notes            VARCHAR(1000) NULL,          -- 설명/비고

    file\_group\_id    CHAR(10)     NULL,

    created\_at       TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    updated\_at       TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, inspection\_id),

    CONSTRAINT fk\_insp\_plant     FOREIGN KEY (company\_id, plant\_id) REFERENCES plant\_master(company\_id, plant\_id) ON DELETE SET NULL,

    CONSTRAINT fk\_insp\_dept      FOREIGN KEY (company\_id, dept\_id)  REFERENCES platform.dept(company\_id, dept\_id) ON DELETE SET NULL,

    CONSTRAINT fk\_insp\_job\_code  FOREIGN KEY (company\_id, job\_code\_type, job\_code\_id) REFERENCES code(company\_id, code\_type, code\_id) ON DELETE SET NULL

);



CREATE TABLE inspection\_item (

&nbsp;   company\_id      CHAR(5)       NOT NULL,

&nbsp;   inspection\_id   CHAR(10)      NOT NULL,

&nbsp;   item\_no         INT           NOT NULL,        -- 항목 순번(1..N)

&nbsp;   item\_name       VARCHAR(200)  NOT NULL,        -- 항목명

&nbsp;   method          VARCHAR(200)  NULL,            -- 점검 방법/절차

&nbsp;   unit            VARCHAR(20)   NULL,            -- 단위(필요 시 코드화 가능)

&nbsp;   min\_val         DECIMAL(18,4) NULL,            -- 허용 최소

&nbsp;   max\_val         DECIMAL(18,4) NULL,            -- 허용 최대

&nbsp;   std\_val         DECIMAL(18,4) NULL,            -- 표준값(목표)

&nbsp;   result\_val      DECIMAL(18,4) NULL,            -- 수치 결과(정량)

&nbsp;   result\_text     VARCHAR(300)  NULL,            -- 텍스트 결과(정성)

&nbsp;   note            VARCHAR(500)  NULL,            -- 비고 (헤더는 notes 사용 권장)

&nbsp;   -- 결과 저장 2단계: TEMP(임시), CONFIRMED(확정)

&nbsp;   result\_status   VARCHAR(10)   NULL,            -- 'TEMP' | 'CONFIRMED'

&nbsp;   confirmed\_by    CHAR(5)       NULL,            -- 확정자(user\_id)

&nbsp;   confirmed\_at    TIMESTAMP     NULL,            -- 확정 시각

&nbsp;   created\_at      TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP,

&nbsp;   updated\_at      TIMESTAMP     NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,



&nbsp;   PRIMARY KEY (company\_id, inspection\_id, item\_no),



&nbsp;   CONSTRAINT fk\_insp\_item\_head

&nbsp;       FOREIGN KEY (company\_id, inspection\_id)

&nbsp;       REFERENCES inspection(company\_id, inspection\_id)

&nbsp;       ON DELETE CASCADE,



&nbsp;   -- 확정자: platform.user\_account(company\_id, user\_id) 참조

&nbsp;   CONSTRAINT fk\_insp\_item\_confirm\_user

&nbsp;       FOREIGN KEY (company\_id, confirmed\_by)

&nbsp;       REFERENCES platform.user\_account(company\_id, user\_id)

&nbsp;       ON DELETE SET NULL

);

-- =========================

-- 작업지시 (WorkOrder)

-- =========================

CREATE TABLE workorder (

    company\_id            CHAR(5)      NOT NULL,

    workorder\_id          CHAR(10)     NOT NULL,

    title                 VARCHAR(100) NOT NULL,

    plant\_id              CHAR(10)     NULL,

    dept\_id               CHAR(5)      NULL,

    planned\_date          DATE         NULL,

    performed\_date        DATE         NULL,

    planned\_cost          DECIMAL(18,2) NULL,

    performed\_cost           DECIMAL(18,2) NULL,

    planned\_labor\_hours   DECIMAL(18,2) NULL,

    performed\_labor\_hours    DECIMAL(18,2) NULL,

    notes                 VARCHAR(1000) NULL,

    file\_group\_id         CHAR(10)     NULL,

    status                VARCHAR(20)  NULL,          -- TEMP/CONFIRMED 등

    created\_at            TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    updated\_at            TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, workorder\_id),

    CONSTRAINT fk\_wo\_plant  FOREIGN KEY (company\_id, plant\_id) REFERENCES plant\_master(company\_id, plant\_id) ON DELETE SET NULL,

    CONSTRAINT fk\_wo\_dept   FOREIGN KEY (company\_id, dept\_id)  REFERENCES platform.dept(company\_id, dept\_id) ON DELETE SET NULL

);



CREATE TABLE workorder\_item (

    company\_id         CHAR(5)      NOT NULL,

    workorder\_id       CHAR(10)     NOT NULL,

    item\_no            INT          NOT NULL,

    item\_name          VARCHAR(100) NOT NULL,

    method             VARCHAR(100) NULL,

    -- 실행 정보(라인 레벨)

    actual\_labor         DECIMAL(18,4) NULL,

    actual\_hours       DECIMAL(18,2) NULL,

    actual\_cost        DECIMAL(18,2) NULL,

    notes               VARCHAR(500)  NULL,

    PRIMARY KEY (company\_id, workorder\_id, item\_no),

    CONSTRAINT fk\_wo\_item FOREIGN KEY (company\_id, workorder\_id) REFERENCES workorder(company\_id, workorder\_id) ON DELETE CASCADE

);



-- (합계 Roll-up은 View/Service에서 SUM(actual\_\*)로 계산 권장)



-- =========================

-- 작업허가 (WorkPermit) + 서명정보(아이템)

-- =========================

CREATE TABLE workpermit (

    company\_id    CHAR(5)      NOT NULL,

    permit\_id     CHAR(10)     NOT NULL,

    title         VARCHAR(100) NOT NULL,

    plant\_id      CHAR(10)     NULL,

    dept\_id       CHAR(5)      NULL,

    job\_code\_type VARCHAR(20)  NULL DEFAULT 'JOBTP',

    job\_code\_id   CHAR(5)      NULL,

    planned\_time    DATETIME     NULL,

    end\_time      DATETIME     NULL,

    notes         VARCHAR(1000) NULL,

    file\_group\_id CHAR(10)     NULL,

    status        VARCHAR(20)  NULL,         -- DRAFT/APPROVAL/APPROVED 등

    created\_at    TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP,

    updated\_at    TIMESTAMP    NOT NULL DEFAULT CURRENT\_TIMESTAMP ON UPDATE CURRENT\_TIMESTAMP,

    PRIMARY KEY (company\_id, permit\_id),

    CONSTRAINT fk\_wp\_plant    FOREIGN KEY (company\_id, plant\_id) REFERENCES plant\_master(company\_id, plant\_id) ON DELETE SET NULL,

    CONSTRAINT fk\_wp\_dept     FOREIGN KEY (company\_id, dept\_id)  REFERENCES platform.dept(company\_id, dept\_id) ON DELETE SET NULL,

    CONSTRAINT fk\_wp\_job\_code FOREIGN KEY (company\_id, job\_code\_type, job\_code\_id) REFERENCES code(company\_id, code\_type, code\_id) ON DELETE SET NULL

);



CREATE TABLE workpermit\_item (

    company\_id    CHAR(5)  NOT NULL,

    permit\_id     CHAR(10) NOT NULL,

    item\_no       INT      NOT NULL,

    description   VARCHAR(100) NULL,

    -- 서명 정보

    signer\_name   VARCHAR(100) NULL,

    signer\_sign   TEXT         NULL,   -- 서명 데이터(베이스64 등)

    signed\_at     TIMESTAMP    NULL,

    PRIMARY KEY (company\_id, permit\_id, item\_no),

    CONSTRAINT fk\_wp\_item FOREIGN KEY (company\_id, permit\_id) REFERENCES workpermit(company\_id, permit\_id) ON DELETE CASCADE

);

