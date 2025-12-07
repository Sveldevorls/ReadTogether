DROP TABLE IF EXISTS publishers;

CREATE TABLE publishers (
  -- Meta --
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  search_key VARCHAR(100) NOT NULL, -- String to compared against when searching

  -- Publisher data --
  publisher_name VARCHAR(100) NOT NULL UNIQUE,

  PRIMARY KEY (id)
)

/* Publisher will not have its own /publisher/ page, but only displayed under book details as a string */