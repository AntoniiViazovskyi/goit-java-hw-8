create table worker
(
    ID       SERIAL primary key,
    NAME     VARCHAR(1000) CHECK (LENGTH(NAME) >= 2 AND LENGTH(NAME) <= 1000),
    BIRTHDAY DATE CHECK (BIRTHDAY > '1900-01-01'),
    LEVEL    VARCHAR CHECK (LEVEL in ('Trainee', 'Junior', 'Middle', 'Senior')),
    SALARY   INTEGER CHECK (SALARY > 100 and SALARY < 100000)
);

create table client
(
    ID   SERIAL primary key,
    NAME VARCHAR(1000) CHECK (LENGTH(NAME) >= 2 AND LENGTH(NAME) <= 1000)
);

create table project
(
    ID          SERIAL primary key,
    CLIENT_ID   INTEGER,
    START_DATE  DATE,
    FINISH_DATE DATE
);

create table project_worker
(
    PROJECT_ID INTEGER,
    WORKER_ID  INTEGER,
    primary key (PROJECT_ID, WORKER_ID),
    foreign key (PROJECT_ID) references project (id),
    foreign key (WORKER_ID) references worker (id)
);