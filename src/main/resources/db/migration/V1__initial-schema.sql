-- category
CREATE SEQUENCE category_seq;
CREATE TABLE category
(
    id       BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('category_seq'::regclass),
    name     VARCHAR(255)                NOT NULL,
    icon_url VARCHAR(255)                NOT NULL,

    created  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated  TIMESTAMP WITHOUT TIME ZONE
);

-- account
CREATE SEQUENCE account_seq;
CREATE TABLE account
(
    id           BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('account_seq'::regclass),
    external_id  VARCHAR(255) UNIQUE,
    name         VARCHAR(255)                NOT NULL,
    first_name   VARCHAR(255)                NOT NULL,
    last_name    VARCHAR(255)                NOT NULL,
    email        VARCHAR(255)                NOT NULL UNIQUE,
    icon_url     VARCHAR(255)                NOT NULL,
    service_user BOOLEAN                     NOT NULL,

    last_sync    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated      TIMESTAMP WITHOUT TIME ZONE
);

-- announcement
CREATE SEQUENCE announcement_seq;
CREATE TABLE announcement
(
    id        BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('announcement_seq'::regclass),
    subject   VARCHAR(255)                NOT NULL,
    content   TEXT                        NOT NULL,
    author_id BIGINT                      NOT NULL REFERENCES account (id),

    created   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated   TIMESTAMP WITHOUT TIME ZONE
);
