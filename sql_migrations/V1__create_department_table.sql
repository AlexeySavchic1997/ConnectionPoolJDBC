create table if not exists departments
(
id uuid primary key default uuidv7(),
department_name varchar(50) unique not null
);
