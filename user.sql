-- auto-generated definition
create table user
(
    id              serial  not null
        constraint user_pk
            primary key,
    birthday        timestamp,
    first_name      varchar not null,
    last_name       varchar not null,
    role            varchar not null,
    school_class_id integer
        constraint user_school_class_id_fkey
            references school_class,
    middle_name     varchar not null,
    login           varchar not null,
    password        varchar not null
);

alter table user
    owner to postgres;

