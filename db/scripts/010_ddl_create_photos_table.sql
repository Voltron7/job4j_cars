create table if not exists photos
(
    id     serial primary key,
    path   varchar,
    car_id int references cars(id)
);