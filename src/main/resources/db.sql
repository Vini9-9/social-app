CREATE DATABASE "social-app";

CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    username VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    birth_date DATE,
    remaining_characters INTEGER,
    blocked boolean,
    PRIMARY KEY (id)
);

CREATE TABLE posts (
    id BIGSERIAL PRIMARY KEY,
    text VARCHAR(250) NOT NULL,
    dateTime TIMESTAMP,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
