drop table T_STUDENT if exists;
drop table T_C_CATEG if exists;
drop table T_E_COURSE if exists;


create table T_STUDENT (NAME varchar(250) primary key, GRADE number, S_YEAR number, F_SECTION varchar(100));
create table T_C_CATEG (ID varchar(100) primary key, DESCR varchar(16));
create table T_E_COURSE (ID INT AUTO_INCREMENT NOT NULL, TITLE varchar(250), MAX_A number, S_YEAR number, CAT_ID varchar(100), TEACHER varchar(100));

alter table T_E_COURSE add constraint FK_CAT foreign key (CAT_ID) references T_C_CATEG(ID) on delete cascade;