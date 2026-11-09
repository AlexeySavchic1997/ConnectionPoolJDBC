insert into orders (order_name, summary_cost, user_id, item_id)
values
('Order1', 10000, (select id from users where username='Jenya'), (select id from items where name='item4')),
('Order2', 10000, (select id from users where username='Gena'), (select id from items where name='item12')),
('Order3', 10000, (select id from users where username='Lesha'), (select id from items where name='item1')),
('Order4', 10000, (select id from users where username='Masha'), (select id from items where name='item8')),
('Order5', 10000, (select id from users where username='Kostya'), (select id from items where name='item15')),
('Order6', 10000, (select id from users where username='Dasha'), (select id from items where name='item2')),
('Order7', 10000, (select id from users where username='Artyom'), (select id from items where name='item11')),
('Order8', 5000, (select id from users where username='Kirill'), (select id from items where name='item7')),
('Order9', 5000, (select id from users where username='Sanya'), (select id from items where name='item16')),
('Order10', 5000, (select id from users where username='Nikita'), (select id from items where name='item5')),
('Order11', 5000, (select id from users where username='Petya'), (select id from items where name='item9')),
('Order12', 5000, (select id from users where username='Katya'), (select id from items where name='item3')),
('Order13', 5000, (select id from users where username='Ura'), (select id from items where name='item14')),
('Order14', 5000, (select id from users where username='Yaric'), (select id from items where name='item10'));