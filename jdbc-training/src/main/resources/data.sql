create table person
(
id integer not null,
name varchar(255) not null,
location varchar(255),
birth_date timestamp,
primary key(id)
);

insert into person (id, name, location, birth_date)
VALUES (10001, 'Rafal', 'Warsaw', sysdate());
insert into person (id, name, location, birth_date)
VALUES (10002, 'James', 'NYC', sysdate());
insert into person (id, name, location, birth_date)
VALUES (10003, 'Peter', 'Amsterdam', sysdate());
--insert into person (id, name, location, birth_date)
--VALUES (10004, 'KtosTam', 'Amsterdam', sysdate());