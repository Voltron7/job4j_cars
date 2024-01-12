create table if not exists participates
(
    id           serial primary key,
    auto_post_id int    not null references auto_posts(id),
    auto_user_id int    not null references auto_users(id),
    UNIQUE (auto_post_id, auto_user_id)
);