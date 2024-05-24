

insert into CourseCategory (category, description) values ('FE', 'FE');
insert into CourseCategory (category, description) values ('BE', 'BE');
insert into CourseCategory (category, description) values ('DevOps', 'DevOps');

insert into ElectiveCourse (id, title, maxAllowedStudents, studyYear, category, teacherName)
values (1, 'OOP', 5, 3, 'FE', 'Shops');

insert into ElectiveCourse (id, title, maxAllowedStudents, studyYear, category, teacherName)
values (2, 'DB', 5, 3, 'BE', 'Popa');




select * from course_category;
insert into elective_course (id, title, max_allowed_students, study_year, category, teacher_name)
values (1, 'OOP', 25, 3, 'FE', 'Shops');

insert into elective_course (id, title, max_allowed_students, study_year, category, teacher_name)
values (2, 'noSql-DB', 40, 3, 'BE', 'Popa');
select * from elective_course;