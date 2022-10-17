CREATE SEQUENCE _user_seq;
CREATE TABLE _user
(
    id          BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('_user_seq'::regclass),
    external_id VARCHAR(255)                NOT NULL,
    user_name   VARCHAR(255)                NOT NULL,
    email       VARCHAR(255)                NOT NULL,
    first_name  VARCHAR(255)                NOT NULL,
    last_name   VARCHAR(255)                NOT NULL,
    created     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated     TIMESTAMP WITHOUT TIME ZONE
);

CREATE SEQUENCE _group_seq;
CREATE TABLE _group
(
    id      BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('_group_seq'::regclass),
    name    VARCHAR(255)                NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE group_user_relation
(
    group_id BIGINT NOT NULL,
    user_id  BIGINT NOT NULL,
    CONSTRAINT fk_mapping_group FOREIGN KEY (group_id) REFERENCES _group (id),
    CONSTRAINT fk_mapping_user FOREIGN KEY (user_id) REFERENCES _user (id)
);
