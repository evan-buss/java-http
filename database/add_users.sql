-- Inserts the dummy data to start the database

INSERT INTO users (name, age, role, created_on)
VALUES
  ('Evan Buss', 21, 'Leader', current_timestamp),
  ('Adam Buss', 22, 'Lead Creative', current_timestamp),
  ('Ian Buss', 18, 'The Wildcard', current_timestamp),
  ('Kristine Buss', 50, 'The Mother', current_timestamp),
  ('Michael Buss', 51, 'The Father', current_timestamp);
