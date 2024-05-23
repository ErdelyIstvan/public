drop table T_STUDENT if exists;
drop table T_C_CATEG if exists;
drop table T_E_COURSE if exists;


create table Student (NAME varchar(250) primary key, GRADE float, S_YEAR int, F_SECTION varchar(100));
create table CourseCategory (category varchar(100) primary key, description varchar(250));
create table ElectiveCourse (id INT NOT NULL, title varchar(250), maxAllowedStudents int, studyYear int, category varchar(100), teacherName varchar(100), unique(id));

alter table ElectiveCourse add constraint FK_CAT foreign key (category) references CourseCategory(category) on delete cascade;