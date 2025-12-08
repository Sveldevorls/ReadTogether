-- /submissions/author/[id]
DROP TABLE IF EXISTS author_submissions;

CREATE TABLE author_submissions (
  -- Meta --
  id INT NOT NULL AUTO_INCREMENT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  -- Submission data --
  submission_type ENUM('new', 'update') NOT NULL,
  previous_submission_id INT, -- ID of previous approved submisison for comparison purpose, NULL for new submissions
  author_id INT, -- ID of original author in authors table
  submitter_id INT NOT NULL,
  submitter_comment TEXT,
  reviewer_id INT,
  reviewer_comment TEXT,
  reviewed_at TIMESTAMP,
  review_status ENUM('approved', 'pending', 'rejected') NOT NULL DEFAULT 'pending',
  
  -- Submission data --
  author_name VARCHAR(255) NOT NULL,
  date_of_birth DATE,
  date_of_death DATE,
  author_image_url VARCHAR(500),
  biography TEXT,

  PRIMARY KEY (id)
);

/* 
New submission:
  submission_type = new,
  previous_submisison_id = NULL,
  author_id = NULL

Update submission:
  submission_type = update,
  previous_submission_id = [id of latest approved submission]
  author_id = [author.id]
*/