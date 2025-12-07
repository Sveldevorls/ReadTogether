DROP TABLE IF EXISTS books;

CREATE TABLE books (
  -- Meta --
  id INT NOT NULL AUTO_INCREMENT,
  search_key VARCHAR(500) NOT NULL, -- String to compared against when searching
  slug VARCHAR(500) UNIQUE NOT NULL, -- URL, [id]-[title]
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Book data --
  book_status ENUM('approved', 'pending') DEFAULT 'pending',
  title VARCHAR(500) NOT NULL,
  isbn VARCHAR(13) UNIQUE,
  book_description TEXT,
  publisher_id INT, -- Enforcing one publisher per book right now, since this is the norm
  published_date DATE,
  cover_url VARCHAR(500),
  score_sum INT DEFAULT 0,
  rating_count INT DEFAULT 0,
  review_count INT DEFAULT 0,
  
  PRIMARY KEY (id)
)