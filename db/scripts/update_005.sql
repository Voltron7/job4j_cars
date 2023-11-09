ALTER TABLE auto_posts
ADD COLUMN if not exists price_history_id int REFERENCES price_history (id) not null;
 