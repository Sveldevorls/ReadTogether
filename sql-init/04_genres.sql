-- /genres/[slug]
DROP TABLE IF EXISTS genres;

CREATE TABLE genres (
  -- Meta
  id INT NOT NULL AUTO_INCREMENT,
  -- search_key VARCHAR(100) NOT NULL, -- String to compare against when searching
  slug VARCHAR(100) UNIQUE NOT NULL, -- URL
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Genre data
  genre_name VARCHAR(100) NOT NULL,

  PRIMARY KEY (id)
);