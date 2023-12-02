create table if not exists history_owners
(
    id serial primary key,
    owner_id int not null references owners(id),
    car_id   int not null references cars(id),
    startAt  timestamp,
    endAt    timestamp
);