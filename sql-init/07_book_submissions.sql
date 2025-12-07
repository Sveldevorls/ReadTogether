DROP TABLE IF EXISTS book_submissions;

CREATE TABLE book_submissions (
  -- Meta --
  id INT NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Submission data --
  user_id INT NOT NULL,
  submission_type ENUM('new', 'update') NOT NULL,
  related_resource VARCHAR(500), -- For update submissions, value = original book slug
  review_status ENUM('approved', 'pending', 'rejected') NOT NULL DEFAULT 'pending',
  reviewer_id INT,
  reviewer_comment TEXT,
  reviewed_at TIMESTAMP,
  
  -- Book data --
  title VARCHAR(500) NOT NULL,
  isbn VARCHAR(13) UNIQUE,
  book_description TEXT,
  publisher_id INT,
  published_date DATE,
  cover_url VARCHAR(500),

  PRIMARY KEY (id)
)