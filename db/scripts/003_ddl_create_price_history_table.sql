create table if not exists price_history
(
    id           serial primary key,
    before       int not null,
    after        int not null,
    created      timestamp without time zone default now(),
    auto_post_id int references auto_posts(id)
);