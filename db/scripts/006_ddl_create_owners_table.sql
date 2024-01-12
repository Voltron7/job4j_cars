create table if not exists owners
(
    id           serial primary key,
    ownerName    varchar,
    auto_user_id int references auto_users(id)
);