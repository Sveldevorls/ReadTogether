-- User favorite mapping --
DROP TABLE IF EXISTS user_favorite_author_map;
DROP TABLE IF EXISTS user_favorite_book_map;
DROP TABLE iF EXISTS user_favorite_genre_map;

CREATE TABLE user_favorite_author_map (
  user_id INT NOT NULL,
  author_id INT NOT NULL,
  PRIMARY KEY (user_id, author_id)
);

CREATE TABLE user_favorite_book_map (
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  PRIMARY KEY (user_id, book_id) 
);

CREATE TABLE user_favorite_genre_map (
  user_id INT NOT NULL,
  genre_id INT NOT NULL,
  PRIMARY KEY (user_id, genre_id)
);

-- User book status mapping --
DROP TABLE IF EXISTS user_reading_book_map;
DROP TABLE IF EXISTS user_finished_book_map;

CREATE TABLE user_reading_book_map (
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  PRIMARY KEY (user_id, book_id) 
);

CREATE TABLE user_finished_book_map (
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  PRIMARY KEY (user_id, book_id) 
);

-- Book data mapping --
DROP TABLE IF EXISTS book_author_map;
DROP TABLE IF EXISTS book_genre_map;

CREATE TABLE book_author_map (
  book_id INT NOT NULL,
  author_id INT NOT NULL,
  PRIMARY KEY (book_id, author_id) 
);

CREATE TABLE book_genre_map (
  book_id INT NOT NULL,
  genre_id INT NOT NULL,
  PRIMARY KEY (book_id, genre_id) 
);