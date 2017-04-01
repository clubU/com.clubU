CREATE TABLE IF NOT EXISTS club (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(63) NOT NULL,
    password_hash VARCHAR(127) NOT NULL,
    name VARCHAR(127) NOT NULL,
    email VARCHAR (127) NOT NULL,
    abbreviation VARCHAR(15),
    description TEXT,
    time_created DATETIME NOT NULL,
    time_updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username)
);

