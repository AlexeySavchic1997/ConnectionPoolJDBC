create table if not exists orders
(
id uuid primary key default uuidv7(),
order_name varchar(30) unique not null,
summary_cost integer,
user_id uuid references users (id) on delete cascade,
item_id uuid references items (id) on delete cascade
);