CREATE TABLE `students` (
  `matrikelnr` int UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `vorname` varchar(255) NOT NULL,
  `nachname` varchar(255) NOT NULL,
  `java` ENUM ('None', 'Beginner', 'Intermediate', 'Advanced', 'Sebastian')
);

CREATE TABLE `courses` (
  `id` int UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `room_id` int NOT NULL,
  `description` varchar(255),
  `prof` varchar(255)
);

CREATE TABLE `rooms` (
  `id` int UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `name` varchar(255)
);

CREATE TABLE `student_course` (
  `student_matrikelnr` int NOT NULL,
  `course_id` int NOT NULL
);

ALTER TABLE `student_course` ADD FOREIGN KEY (`student_matrikelnr`) REFERENCES `students` (`matrikelnr`);

ALTER TABLE `student_course` ADD FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`);

ALTER TABLE `courses` ADD FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`);

ALTER TABLE `students` COMMENT = 'Stores students';

ALTER TABLE `courses` COMMENT = 'stores courses';

ALTER TABLE `rooms` COMMENT = 'stores rooms';

ALTER TABLE `student_course` COMMENT = 'junction table for many-to-many relation';
