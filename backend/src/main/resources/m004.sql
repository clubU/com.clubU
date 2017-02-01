CREATE TABLE IF NOT EXISTS event (
    id BIGINT NOT NULL AUTO_INCREMENT,
    club_id BIGINT NOT NULL,
    title VARCHAR(63) NOT NULL,
    time DATETIME NOT NULL,
    location VARCHAR(127) NOT NULL,
    description TEXT NOT NULL,
    time_created DATETIME NOT NULL,
    time_updated DATETIME NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (club_id)
        REFERENCES club(id)
        ON DELETE CASCADE
);

