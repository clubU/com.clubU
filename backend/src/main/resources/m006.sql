CREATE TABLE IF NOT EXISTS notification (
	student_id BIGINT NOT NULL,
	event_id BIGINT NOT NULL,
	PRIMARY KEY (student_id, event_id),
    FOREIGN KEY (student_id)
        REFERENCES student(id)
        ON DELETE CASCADE,
    FOREIGN KEY (event_id)
        REFERENCES event(id)
        ON DELETE CASCADE
);

