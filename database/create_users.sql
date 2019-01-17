CREATE TABLE users(
  user_id SERIAL PRIMARY KEY,
  name text NOT NULL,
  role text NOT NULL,
  created_on TIMESTAMP NOT NULL
);
