DROP TABLE IF EXISTS reviews;

CREATE TABLE reviews (
  -- Meta --
  id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Review data --
  user_id INT NOT NULL,
  book_id INT NOT NULL,
  content TEXT, -- Null for simple ratings
  like_count INT DEFAULT 0,
  rating INT NOT NULL,
  
  PRIMARY KEY (id),
  CHECK (rating >= 1 AND rating <= 5)
)