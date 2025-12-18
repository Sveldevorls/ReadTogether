-- /genres/[slug]
DROP TABLE IF EXISTS genres;

CREATE TABLE genres (
    -- Meta
    id INT NOT NULL AUTO_INCREMENT,
    slug VARCHAR(100) UNIQUE NOT NULL, -- URL
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Genre data
    genre_name VARCHAR(100) NOT NULL,
    
    PRIMARY KEY (id)
);

-- Default sample genres
INSERT INTO genres (genre_name, slug)
VALUES
    ("Art", "art"),
    ("Biography", "biography"),
    ("Business", "business"),
    ("Comics", "comics"),
    ("Cookbooks", "cookbooks"),
    ("Crime", "crime"),
    ("Fantasy", "fantasy"),
    ("Fiction", "fiction"),
    ("Graphic Novels", "graphic-novels"),
    ("Historical Fiction", "historical-fiction"),
    ("History", "history"),
    ("Horror", "horror"),
    ("Humor and Comedy", "humor-and-comedy"),
    ("Manga", "manga"),
    ("Music", "music"),
    ("Mystery", "mystery"),
    ("Nonfiction", "nonfiction"),
    ("Poetry", "poetry"),
    ("Psychology", "psychology"),
    ("Romance", "romance"),
    ("Science", "science"),
    ("Science Fiction", "science-fiction"),
    ("Sports", "sports"),
    ("Thriller", "thriller"),
    ("Travel", "travel");