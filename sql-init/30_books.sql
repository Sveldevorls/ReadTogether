-- /books/[slug]
DROP TABLE IF EXISTS books;

CREATE TABLE books (
    -- Meta
    id INT NOT NULL AUTO_INCREMENT,
    slug VARCHAR(255) NOT NULL, -- URL, [id]-[title], id is the main identifier
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_pending BOOLEAN NOT NULL DEFAULT true,

    -- Book data
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(13),
    book_description TEXT,
    publisher_name VARCHAR(100), -- No plans to add a dedicated /publisher/ page for now
    published_date DATE,
    cover_url VARCHAR(500), 
    
    PRIMARY KEY (id)
);