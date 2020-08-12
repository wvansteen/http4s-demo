CREATE TYPE importance AS ENUM('Low', 'Medium', 'High');
CREATE TABLE todo (
  id SERIAL PRIMARY KEY,
  description TEXT NOT NULL,
  importance importance NOT NULL
);