create table lecturer(
     id serial primary key,
     first_name VARCHAR(30) not null ,
     last_name VARCHAR(30) not null ,
     experience integer not null check ( experience>=0 and experience<=90)
);

create table student(
    id serial primary key,
    first_name VARCHAR(30) not null ,
    last_name VARCHAR(30) not null ,
    numGroup VARCHAR(15) not null
);

create table course(
    id serial primary key,
    name VARCHAR(30) not null,
    start_end_date VARCHAR(30) not null,
    lecturer_id integer not null,
    foreign key (lecturer_id) references lecturer(id)
);

create table student_course(
     id_student integer not null,
     id_course integer not null,
     foreign key (id_student) references student(id),
     foreign key (id_course) references course(id)
);

create table lesson(
    id serial primary key ,
    name VARCHAR(30) not null,
    day_of_week VARCHAR(20) not null,
    id_course integer not null,
    foreign key (id_course) references course(id)
);