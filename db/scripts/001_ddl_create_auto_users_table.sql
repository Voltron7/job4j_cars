create table if not exists auto_users
(
    id       serial primary key,
    login    varchar unique not null,
    password varchar        not null
);