ALTER TABLE owners
ADD COLUMN history_id int not null unique references history(id);