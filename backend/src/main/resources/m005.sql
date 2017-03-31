CREATE TABLE IF NOT EXISTS image (
    id BIGINT NOT NULL AUTO_INCREMENT,
    content LONGBLOB NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE student ADD COLUMN image_id BIGINT DEFAULT NULL;
ALTER TABLE student ADD CONSTRAINT fk_student_image
	FOREIGN KEY (image_id) REFERENCES image(id)
	ON DELETE SET NULL;

ALTER TABLE club ADD COLUMN image_id BIGINT DEFAULT NULL;
ALTER TABLE club ADD CONSTRAINT fk_club_image
	FOREIGN KEY (image_id) REFERENCES image(id)
	ON DELETE SET NULL;

ALTER TABLE event ADD COLUMN image_id BIGINT DEFAULT NULL;
ALTER TABLE event ADD CONSTRAINT fk_event_image
	FOREIGN KEY (image_id) REFERENCES image(id)
	ON DELETE SET NULL;

