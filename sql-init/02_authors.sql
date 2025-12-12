-- /authors/[slug]
DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
  -- Meta
  id INT NOT NULL AUTO_INCREMENT,
  -- search_key VARCHAR(255) NOT NULL, -- String to compare against when searching, generated from author_name
  slug VARCHAR(255) UNIQUE NOT NULL, -- URL, [id]-[author_name], id is the main identifier
  created_from INT NOT NULL, -- The id to the first new author submission in author_submissions that created this record
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Author data
  author_name VARCHAR(255) NOT NULL,
  date_of_birth DATE,
  date_of_death DATE,
  author_image_url VARCHAR(500),
  biography TEXT,

  PRIMARY KEY (id)
);