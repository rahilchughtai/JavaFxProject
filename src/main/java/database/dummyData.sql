use sms;


# Create dummy rooms
insert into rooms (id, name) VALUES (1,'Raum 101');
insert into rooms (id, name) VALUES (2,'Raum 202');
insert into rooms (id, name) VALUES (3,'Raum 303');
insert into rooms (id, name) VALUES (4,'Raum 404');
insert into rooms (id, name) VALUES (5,'Raum 505');




# Create dummy courses
insert into courses
VALUES (1,'Algorithms and Complexity', 1,'Lectures on algorithms, complexity and data structures','Prof. Dr. Karl Stroetmann');
insert into courses
VALUES (2,'Analysis', 2,'Lecture on Analysis','Jonas Fehrenbach');
insert into courses
VALUES (3,'Java Programming', 3,'Lecture on the Programming language "Java" and OOP practices','Sebastian Damm');
insert into courses
VALUES (4,'Project Management', 4,'Lectures to give students a brief overview on how to work on IT Projects in teams','Ulf Runge');
insert into courses
VALUES (5,'Workflowmanagement', 5,'Pure therapy. The best. We love it. We love Martin. God bless him.','Dr. Martin Cierjacks');

# Create dummy students
insert into students (id, vorname, nachname, java)
VALUES (1, 'Jan', 'Baumann', 'Intermediate');
insert into students (id, vorname, nachname, java)
VALUES (2, 'Sebastian', 'Damm', 'Sebastian');
insert into students (id, vorname, nachname, java)
VALUES (3, 'Eckhard', 'Kruse', 'None');
insert into students (id, vorname, nachname, java)
VALUES (4, 'Peter', 'Parker', 'Advanced');
insert into students (id, vorname, nachname, java)
VALUES (5, 'Tony', 'Stark', 'Sebastian');
insert into students (id, vorname, nachname, java)
VALUES (6, 'Mary-Jane', 'Watson', 'Beginner');

-- select * from courses;
-- select * from rooms;
-- select * from students;