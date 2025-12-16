-- /authors/[slug]
DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
    -- Meta
    id INT NOT NULL AUTO_INCREMENT,
    slug VARCHAR(255) NOT NULL, -- URL, [id]-[author_name], id is the main identifier
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    is_pending BOOLEAN NOT NULL DEFAULT true,

    -- Author data
    author_name VARCHAR(255) NOT NULL,
    date_of_birth DATE,
    date_of_death DATE,
    author_image_url VARCHAR(500),
    biography TEXT,
    
    PRIMARY KEY (id)
);