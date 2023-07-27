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
    id              BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('account_seq'::regclass),
    external_id     VARCHAR(255) UNIQUE,
    name            VARCHAR(255)                NOT NULL,
    first_name      VARCHAR(255)                NOT NULL,
    last_name       VARCHAR(255)                NOT NULL,
    email           VARCHAR(255)                NOT NULL UNIQUE,
    icon_url        VARCHAR(255)                NOT NULL,
    service_account BOOLEAN                     NOT NULL,

    last_sync       TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated         TIMESTAMP WITHOUT TIME ZONE
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

-- event
CREATE SEQUENCE event_seq;
CREATE TABLE event
(
    id               BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('event_seq'::regclass),
    owner_id         BIGINT                      NOT NULL REFERENCES account (id),

    start            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    finish           TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    title            VARCHAR(255)                NOT NULL,
    short_text       VARCHAR(255)                NOT NULL,
    long_text        TEXT                        NOT NULL,
    image_url        VARCHAR(255)                NOT NULL,
    icon_url         VARCHAR(255)                NOT NULL,

    has_location     BOOLEAN                     NOT NULL,
    has_registration BOOLEAN                     NOT NULL,
    published        BOOLEAN                     NOT NULL,

    created          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated          TIMESTAMP WITHOUT TIME ZONE
);


-- location
CREATE SEQUENCE location_seq;
CREATE TABLE location
(
    id              BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('location_seq'::regclass),

    event_id        BIGINT                      NOT NULL REFERENCES event (id),

    street          VARCHAR(255)                NOT NULL,
    street_number   VARCHAR(255)                NOT NULL,
    zip             VARCHAR(255)                NOT NULL,
    city            VARCHAR(255)                NOT NULL,
    country         VARCHAR(255)                NOT NULL,
    additional_info VARCHAR(255)                NOT NULL,

    lat             DOUBLE PRECISION            NOT NULL,
    lon             DOUBLE PRECISION            NOT NULL,

    size            INT                         NOT NULL,

    created         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated         TIMESTAMP WITHOUT TIME ZONE
);

-- registration
CREATE SEQUENCE registration_seq;
CREATE TABLE registration
(
    id                 BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('registration_seq'::regclass),
    event_id           BIGINT                      NOT NULL REFERENCES event (id),

    max_guest_amount   INT                         NOT NULL,
    interested_allowed BOOLEAN                     NOT NULL,
    attendant_allowed  BOOLEAN                     NOT NULL,
    tickets_enabled    BOOLEAN                     NOT NULL,

    created            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated            TIMESTAMP WITHOUT TIME ZONE
);
