ALTER TABLE auto_posts
ADD COLUMN car_id int references cars(id);