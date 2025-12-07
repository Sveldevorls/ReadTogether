DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
  -- Meta --
  id INT NOT NULL AUTO_INCREMENT,
  search_key VARCHAR(255) NOT NULL, -- String to compared against when searching
  slug VARCHAR(255) UNIQUE NOT NULL, -- URL, [id]-[author_name]
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Author data --
  author_status ENUM('approved', 'pending') DEFAULT 'pending',
  author_name VARCHAR(255) NOT NULL,
  date_of_birth DATE,
  date_of_death DATE,
  author_image_url VARCHAR(255),
  biography TEXT,
  book_count INT NOT NULL DEFAULT 0,
  fan_count INT NOT NULL DEFAULT 0,

  PRIMARY KEY (id)
);