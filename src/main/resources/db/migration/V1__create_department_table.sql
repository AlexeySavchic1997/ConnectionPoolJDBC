CREATE TABLE IF NOT EXISTS departments
(
id uuid primary key default uuidv7(),
deparment_name varchar(50) unique not null
);
