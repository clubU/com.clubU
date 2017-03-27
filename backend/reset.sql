use clubu;

delete from subscription;
delete from student; 
delete from club;

SET @now = NOW();

INSERT INTO student VALUES(1, "student1", "1", "Student", "One", "student1@student.ca", 101, "1994-08-07", 4, "ECE", @now, @now, NULL);
INSERT INTO student VALUES(2, "student2", "2", "Student", "Two", "student2@student.ca", 102, "1994-08-07", 4, "ECE", @now, @now, NULL);
INSERT INTO student VALUES(3, "student3", "3", "Student", "Three", "student3@student.ca", 103, "1994-08-07", 4, "ECE", @now, @now, NULL);
INSERT INTO student VALUES(4, "student4", "4", "Student", "Four", "student4@student.ca", 104, "1994-08-07", 4, "ECE", @now, @now, NULL);

INSERT INTO club VALUES(1, "club1", "1", "Rock Climbing Madness", "rcm@clubu.ca", "RCM", "", @now, @now, NULL);
INSERT INTO club VALUES(2, "club2", "2", "Mad Scientist", "ms@clubu.ca", "MS", "", @now, @now, NULL);
INSERT INTO club VALUES(3, "club3", "3", "Scientific Magazines", "sm@clubu.ca", "SM", "", @now, @now, NULL);
INSERT INTO club VALUES(4, "club4", "4", "Rock n Roll", "rnr@clubu.ca", "RNR", "", @now, @now, NULL);
INSERT INTO club VALUES(5, "club5", "5", "Roll Over and Die", "road@clubu.ca", "ROAD", "", @now, @now, NULL);
INSERT INTO club VALUES(6, "club6", "6", "Climb to Gold", "ctg@clubu.ca", "CTG", "", @now, @now, NULL);
INSERT INTO club VALUES(7, "club7", "7", "Gold Mine", "gm@clubu.ca", "GM", "", @now, @now, NULL);
INSERT INTO club VALUES(8, "club8", "8", "Minecraft", "mc@clubu.ca", "MC", "", @now, @now, NULL);
INSERT INTO club VALUES(9, "club9", "9", "Craft a Computer", "cac@clubu.ca", "CAC", "", @now, @now, NULL);
INSERT INTO club VALUES(10, "club10", "10", "Computer Engineers Wow", "cew@clubu.ca", "CEW", "", @now, @now, NULL);
INSERT INTO club VALUES(11, "club11", "11", "Wow Doge", "wd@clubu.ca", "WD", "", @now, @now, NULL);
INSERT INTO club VALUES(12, "club12", "12", "Dogs > Cats", "dgtc@clubu.ca", "DGTC", "", @now, @now, NULL);
INSERT INTO club VALUES(13, "club13", "13", "Cats That Can Climb", "ctcc@clubu.ca", "CTCC", "", @now, @now, NULL);
INSERT INTO club VALUES(14, "club14", "14", "Engineers Who Roll", "ewr@clubu.ca", "EWR", "", @now, @now, NULL);
INSERT INTO club VALUES(15, "club15", "15", "ABCDEFGHIJKLMNOPQRSTUVWXYZ", "atz@clubu.ca", "ATZ", "", @now, @now, NULL);

INSERT INTO subscription VALUES(1, 1);
INSERT INTO subscription VALUES(1, 3);
INSERT INTO subscription VALUES(2, 1);
INSERT INTO subscription VALUES(3, 2);
INSERT INTO subscription VALUES(4, 1);
INSERT INTO subscription VALUES(4, 2);
INSERT INTO subscription VALUES(4, 3);

INSERT INTO event VALUES(1, 1, "OTP", @now, "Somewhere", "Something", @now, @now, NULL);
INSERT INTO event VALUES(2, 1, "LOL", @now, "Nowhere", "Nothing", @now, @now, NULL);

