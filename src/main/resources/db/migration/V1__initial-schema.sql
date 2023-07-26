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
