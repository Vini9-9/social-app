CREATE DATABASE "social-app";

CREATE TABLE users (
    id bigserial NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    birth_date DATE,
    remaining_characters INTEGER,
    PRIMARY KEY (id)
);
