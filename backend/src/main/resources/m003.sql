CREATE TABLE IF NOT EXISTS subscription ( 
    student_id BIGINT NOT NULL,
    club_id BIGINT NOT NULL,
    PRIMARY KEY (student_id, club_id),
    FOREIGN KEY (student_id)
        REFERENCES student(id)
        ON DELETE CASCADE,
    FOREIGN KEY (club_id)
        REFERENCES club(id)
        ON DELETE CASCADE
);

