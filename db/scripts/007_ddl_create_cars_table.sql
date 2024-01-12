create table if not exists cars
(
    id        serial primary key,
    model     varchar,
    engine_id int not null references engines(id),
    owner_id  int references owners(id),
    brand_id  int references brands(id),
    body_id   int references bodies(id)
);