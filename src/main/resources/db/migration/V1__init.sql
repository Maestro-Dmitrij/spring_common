create table  IF NOT EXISTS city
(
    id   serial primary key not null,
    name varchar(64)        not null
);

create table  IF NOT EXISTS subject
(
    id   serial primary key not null,
    name varchar(64)        not null
);

create table  IF NOT EXISTS student
(
    id            serial                                    primary key not null,
    name          varchar(64)                               not null,
    сreation_date timestamp without time zone               default now(),
    modify_date   timestamp without time zone,
    city_id       integer references city (id),
    salary        double precision,
    salary_coef   double precision CHECK (salary_coef <= 1) default 0,
    study         boolean                                   default false,
    type          varchar(32)                               not null
);

create table  IF NOT EXISTS student_subject
(
    student_id integer references student (id),
    subject_id integer references subject (id),
    grade      double precision CHECK (0 < grade and grade <= 10),
    primary key (student_id, subject_id)
);