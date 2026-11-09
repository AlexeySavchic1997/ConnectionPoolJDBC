CREATE TABLE IF NOT EXISTS users
(
id uuid primary key default uuidv7(),
username varchar(30) unique not null,
email varchar(50) unique not null,
request_counter integer,
department_id uuid references departments (id)
);
