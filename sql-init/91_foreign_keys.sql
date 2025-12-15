-- Reviews
ALTER TABLE reviews
ADD FOREIGN KEY (user_id) REFERENCES users(id),
ADD FOREIGN KEY (book_id) REFERENCES books(id);

-- Author submissions
ALTER TABLE author_submissions
ADD FOREIGN KEY (previous_submission_id) REFERENCES author_submissions(id),
ADD FOREIGN KEY (author_id) REFERENCES authors(id),
ADD FOREIGN KEY (submitter_id) REFERENCES users(id),
ADD FOREIGN KEY (reviewer_id) REFERENCES users(id);

-- Book submissions
ALTER TABLE book_submissions
ADD FOREIGN KEY (previous_submission_id) REFERENCES book_submissions(id),
ADD FOREIGN KEY (book_id) REFERENCES books(id),
ADD FOREIGN KEY (submitter_id) REFERENCES users(id),
ADD FOREIGN KEY (reviewer_id) REFERENCES users(id);

-- User <-> Favorite authors
ALTER TABLE user_favorite_author_map
ADD FOREIGN KEY (user_id) REFERENCES users(id),
ADD FOREIGN KEY (author_id) REFERENCES authors(id);

-- User <-> Favorite books
ALTER TABLE user_favorite_book_map
ADD FOREIGN KEY (user_id) REFERENCES users(id),
ADD FOREIGN KEY (book_id) REFERENCES books(id);

-- User <-> Favorite genres
ALTER TABLE user_favorite_genre_map
ADD FOREIGN KEY (user_id) REFERENCES users(id),
ADD FOREIGN KEY (genre_id) REFERENCES genres(id);

-- User <-> Book
ALTER TABLE user_reading_book_map
ADD FOREIGN KEY (user_id) REFERENCES users(id),
ADD FOREIGN KEY (book_id) REFERENCES books(id);

-- User <-> Book
ALTER TABLE user_finished_book_map
ADD FOREIGN KEY (user_id) REFERENCES users(id),
ADD FOREIGN KEY (book_id) REFERENCES books(id);

-- Book <-> Author
ALTER TABLE book_author_map
ADD FOREIGN KEY (book_id) REFERENCES books(id),
ADD FOREIGN KEY (author_id) REFERENCES authors(id);

-- Book <-> Genre
ALTER TABLE book_genre_map
ADD FOREIGN KEY (book_id) REFERENCES books(id),
ADD FOREIGN KEY (genre_id) REFERENCES genres(id);