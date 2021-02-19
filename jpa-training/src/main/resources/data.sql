INSERT INTO person_jpa (id, name, location, birth_date)
VALUES (10001, 'Rafal', 'Warsaw', sysdate());
INSERT INTO person_jpa (id, name, location, birth_date)
VALUES (10002, 'James', 'NYC', sysdate());
INSERT INTO person_jpa (id, name, location, birth_date)
VALUES (10003, 'Peter', 'Amsterdam', sysdate());
--------------------------------------------------------------
INSERT INTO course (id, name, created_date, last_updated_date)
VALUES (10001, 'Jpa in 50 steps', sysdate(), sysdate());
INSERT INTO course (id, name, created_date, last_updated_date)
VALUES (10002, 'Jpa in 100 steps', sysdate(), sysdate());
INSERT INTO course (id, name, created_date, last_updated_date)
VALUES (10003, 'Jpa in 150 steps', sysdate(), sysdate());
INSERT INTO course (id, name, created_date, last_updated_date)
VALUES (10004, 'Jpa in 200 steps', sysdate(), sysdate());
--------------------------------------------------------------
INSERT INTO passport (id, number)
VALUES (40001, 'E12345');
INSERT INTO passport (id, number)
VALUES (40002, 'L45678678');
INSERT INTO passport (id, number)
VALUES (40003, 'EL34567');
--------------------------------------------------------------
INSERT INTO student (id, name, passport_id)
VALUES (20001, 'Rafal', 40001);
INSERT INTO student (id, name, passport_id)
VALUES (20002, 'Agata', 40002);
INSERT INTO student (id, name, passport_id)
VALUES (20003, 'Bob', 40003);
--------------------------------------------------------------
INSERT INTO review (id, rating, description, course_id, student_id)
VALUES (50001, '5', 'Great Course', 10001, 20001);
INSERT INTO review (id, rating, description, course_id, student_id)
VALUES (50002, '4', 'Wonderful Course',10001, 20001);
INSERT INTO review (id, rating, description, course_id, student_id)
VALUES (50003, '5', 'Awesome Course',10003, 20002);
--------------------------------------------------------------
INSERT INTO student_course (student_id, course_id)
VALUES (20001, 10001);
INSERT INTO student_course (student_id, course_id)
VALUES (20002, 10001);
INSERT INTO student_course (student_id, course_id)
VALUES (20003, 10001);
INSERT INTO student_course (student_id, course_id)
VALUES (20001, 10003);