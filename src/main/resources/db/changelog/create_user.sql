CREATE TABLE users (
                       id VARCHAR(255) PRIMARY KEY,
                       firstname VARCHAR(255) NOT NULL,
                       lastname VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       user_role VARCHAR(10) NOT NULL CHECK (user_role IN ('STUDENT', 'ADMIN', 'TEACHER'))
);