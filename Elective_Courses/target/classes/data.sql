insert into T_STUDENT (NAME, GRADE, S_YEAR, F_SECTION) values ('John Wick', 5.7, 2, 'ETC');
insert into T_STUDENT (NAME, GRADE, S_YEAR, F_SECTION) values ('Johana Wick', 9.7, 2, 'ETC');
insert into T_STUDENT (NAME, GRADE, S_YEAR, F_SECTION) values ('Johny Wick', 8.7, 3, 'ETC');
insert into T_STUDENT (NAME, GRADE, S_YEAR, F_SECTION) values ('John Braun', 5.7, 4, 'ETC');

insert into T_C_CATEG (ID, DESCR) values ('FE', 'FE');
insert into T_C_CATEG (ID, DESCR) values ('BE', 'BE');
insert into T_C_CATEG (ID, DESCR) values ('DevOps', 'DevOps');

insert into T_E_COURSE (ID, TITLE, MAX_A, S_YEAR, CAT_ID, TEACHER)
values (1, 'OOP', 5, 3, 'FE', 'Shops');

insert into T_E_COURSE (ID, TITLE, MAX_A, S_YEAR, CAT_ID, TEACHER)
values (2, 'DB', 5, 3, 'BE', 'Popa');