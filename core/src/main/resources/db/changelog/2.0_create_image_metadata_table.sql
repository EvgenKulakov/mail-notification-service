--liquibase formatted sql

--changeset EvgeniyKulakov:1
CREATE TABLE IF NOT EXISTS image_metadata
(
    id          SERIAL8 PRIMARY KEY           NOT NULL,
    name        TEXT UNIQUE                   NOT NULL,
    upload_date DATE                          NOT NULL,
    size        INT8                          NOT NULL,
    account_id  INT8 REFERENCES accounts (id) NOT NULL
);