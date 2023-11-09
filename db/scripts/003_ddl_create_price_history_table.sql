create table if not exists price_history
(
    id      serial primary key,
    before  bigint not null,
    after   bigint not null,
    created timestamp without time zone default now()
);