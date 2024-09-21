--liquibase formatted sql
--changeset Minich:1
CREATE TABLE IF NOT EXISTS cars
(
    id          UUID                 DEFAULT gen_random_uuid() PRIMARY KEY,
    name        VARCHAR(10) NOT NULL,
    description VARCHAR(30),
    price       NUMERIC     NOT NULL,
    created     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);