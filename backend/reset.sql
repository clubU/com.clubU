use clubu;

delete from subscription;
delete from student; 
delete from club;

SET @now = NOW();

INSERT INTO student VALUES(1, "student1", "1", "Student", "One", "student1@student.ca", 101, "1994-08-07", 4, "ECE", @now, @now);
INSERT INTO student VALUES(2, "student2", "2", "Student", "Two", "student2@student.ca", 102, "1994-08-07", 4, "ECE", @now, @now);
INSERT INTO student VALUES(3, "student3", "3", "Student", "Three", "student3@student.ca", 103, "1994-08-07", 4, "ECE", @now, @now);
INSERT INTO student VALUES(4, "student4", "4", "Student", "Four", "student4@student.ca", 104, "1994-08-07", 4, "ECE", @now, @now);

INSERT INTO club VALUES(1, "club1", "1", "Club One", "club1@club.ca", "C1", "Hello, this is Club One!", @now, @now);
INSERT INTO club VALUES(2, "club2", "2", "Club Two", "club2@club.ca", "C2", "Hello, this is Club Two!", @now, @now);
INSERT INTO club VALUES(3, "club3", "3", "Club Three", "club3@club.ca", "C3", "Hello, this is Club Three!", @now, @now);

INSERT INTO subscription VALUES(1, 1, @now);
INSERT INTO subscription VALUES(1, 3, @now);
INSERT INTO subscription VALUES(2, 1, @now);
INSERT INTO subscription VALUES(3, 2, @now);
INSERT INTO subscription VALUES(4, 1, @now);
INSERT INTO subscription VALUES(4, 2, @now);
INSERT INTO subscription VALUES(4, 3, @now);

