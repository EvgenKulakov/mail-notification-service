--liquibase formatted sql

--changeset EvgeniyKulakov:1
CREATE TABLE IF NOT EXISTS accounts
(
    id            SERIAL                                            NOT NULL,
    username      VARCHAR(50)                                       NOT NULL,
    password_hash VARCHAR(68)                                       NOT NULL,
    role          VARCHAR(20) CHECK (role IN ('USER', 'MODERATOR')) NOT NULL,
    email         VARCHAR(255)                                      NOT NULL
);