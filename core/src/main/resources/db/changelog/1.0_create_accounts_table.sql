--liquibase formatted sql

--changeset EvgeniyKulakov:1
CREATE TABLE IF NOT EXISTS accounts
(
    id            SERIAL8 PRIMARY KEY                                         NOT NULL,
    username      VARCHAR(50) UNIQUE                                          NOT NULL,
    password_hash VARCHAR(68)                                                 NOT NULL,
    role          VARCHAR(20) CHECK (role IN ('ROLE_USER', 'ROLE_MODERATOR')) NOT NULL,
    email         VARCHAR(255) UNIQUE                                         NOT NULL,
    is_block      BOOL DEFAULT FALSE
);