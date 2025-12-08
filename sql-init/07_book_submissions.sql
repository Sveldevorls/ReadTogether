-- /submissions/books/[id]
DROP TABLE IF EXISTS book_submissions;

CREATE TABLE book_submissions (
  -- Meta
  id INT NOT NULL AUTO_INCREMENT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Submission data
  submission_type ENUM('new', 'update') NOT NULL,
  previous_submission_id INT, -- ID of previous approved submisison for comparison purpose, NULL for new submissions
  book_id INT, -- ID of original book in books table
  submitter_id INT NOT NULL,
  submitter_comment TEXT,
  reviewer_id INT,
  reviewer_comment TEXT,
  reviewed_at TIMESTAMP,
  review_status ENUM('approved', 'pending', 'rejected') NOT NULL DEFAULT 'pending',
  
  -- Submission data
  title VARCHAR(255) NOT NULL,
  isbn VARCHAR(13),
  book_description TEXT,
  publisher_id INT,
  published_date DATE,
  cover_url VARCHAR(500),

  PRIMARY KEY (id)
);

/* 
New submission:
  submission_type = new,
  previous_submisison_id = NULL,
  book_id = NULL

Update submission:
  submission_type = update,
  previous_submission_id = [id of latest approved submission]
  book_id = [book.id]
*/