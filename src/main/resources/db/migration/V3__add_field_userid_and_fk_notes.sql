ALTER table notes ADD COLUMN user_id BIGINT NOT NULL,
ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(id);