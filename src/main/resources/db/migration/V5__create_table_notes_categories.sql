CREATE TABLE notes_categories (
     note_id BIGINT NOT NULL,
     category_id BIGINT NOT NULL,
     PRIMARY KEY (note_id, category_id),
     FOREIGN KEY (note_id) REFERENCES notes (id),
     FOREIGN KEY (category_id) REFERENCES categories (id)
);

