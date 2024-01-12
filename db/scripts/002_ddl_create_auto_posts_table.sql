create table if not exists auto_posts
(
    id           serial    primary key,
    description  varchar   not null,
    created      timestamp not null,
    price        int,
    phone        varchar,
    status       boolean,
    auto_user_id int references auto_users(id)
);