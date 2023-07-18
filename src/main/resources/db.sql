CREATE DATABASE social-app;

CREATE TABLE users (
    username VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL,
    birth_date DATE,
    remaining_caracteres INTEGER,
    PRIMARY KEY (username)
);
