create table if not exists history
(
    id       serial primary key,
    startAt  timestamp,
    endtAt   timestamp
);