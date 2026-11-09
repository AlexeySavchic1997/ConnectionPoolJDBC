create table if not exists items
(
id uuid primary key default uuidv7(),
name varchar(50) unique not null,
price integer not null
);