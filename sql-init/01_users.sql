-- /users/[username]
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  -- Meta
  id INT NOT NULL AUTO_INCREMENT,
  search_key VARCHAR(100) NOT NULL DEFAULT (`username`), -- String to compare against when searching, default value (no display name set) = username, replaced by display name once added
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  
  -- User data
  username VARCHAR(20) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL UNIQUE,
  display_name VARCHAR(100),
  password_hash VARCHAR(255) NOT NULL,
  avatar_url VARCHAR(500),
  bio TEXT,
  user_role ENUM('USER', 'MODERATOR', 'ADMIN') NOT NULL DEFAULT 'USER',

  PRIMARY KEY (id)
);