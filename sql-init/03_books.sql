-- /books/[slug]
DROP TABLE IF EXISTS books;

CREATE TABLE books (
  -- Meta
  id INT NOT NULL AUTO_INCREMENT,
  search_key VARCHAR(255) NOT NULL, -- String to compare against when searching, generated from title
  slug VARCHAR(255) UNIQUE NOT NULL, -- URL, [id]-[title], id is the main identifier
  created_from INT NOT NULL, -- The id to the first new book submission in book_submissions that created this record
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Book data
  title VARCHAR(255) NOT NULL,
  isbn VARCHAR(13),
  book_description TEXT,
  publisher_name VARCHAR(100), -- No plans to add a dedicated /publisher/ page for now
  published_date DATE,
  cover_url VARCHAR(500),
  score_sum INT DEFAULT 0,
  rating_count INT DEFAULT 0,
  review_count INT DEFAULT 0,
  
  PRIMARY KEY (id)
);