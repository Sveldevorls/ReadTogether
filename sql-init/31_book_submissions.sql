-- /submissions/books/[id]
DROP TABLE IF EXISTS book_submissions;

CREATE TABLE book_submissions (
    -- Meta
    id INT NOT NULL AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    -- Submission data
    previous_submission_id INT, -- ID of previous approved submisison for comparison, NULL for new submissions
    book_id INT NOT NULL, -- ID of original author in authors table
    submitter_id INT NOT NULL,
    submitter_comment TEXT,
    reviewer_id INT,
    reviewer_comment TEXT,
    reviewed_at TIMESTAMP,
    review_status ENUM('approved', 'pending', 'rejected') NOT NULL DEFAULT 'pending',

    -- Book data
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(13),
    book_description TEXT,
    publisher_name VARCHAR(100), -- No plans to add a dedicated /publisher/ page for now
    published_date DATE,
    cover_url VARCHAR(500), 

    PRIMARY KEY (id)
);

/* 
New submission:
  previous_submisison_id = NULL,

Update submission:
  previous_submission_id = [id of latest approved submission]
*/