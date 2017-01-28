CREATE TABLE IF NOT EXISTS student (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(63) NOT NULL,
    password_hash VARCHAR(127) NOT NULL,
    first_name VARCHAR(127) NOT NULL,
    last_name VARCHAR(127) NOT NULL,
    email VARCHAR(127) NOT NULL,
    student_number BIGINT NOT NULL,
    date_of_birth DATE,
    year_of_study INT,
    program_of_study VARCHAR(63),
    time_created DATETIME NOT NULL,
    time_updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username)
);

