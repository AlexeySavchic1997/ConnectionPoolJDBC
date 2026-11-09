insert into users (username, email, request_counter, department_id)
values
('Lesha', 'email1@gmail.com', 0, (select id from departments where deparment_name='IT')),
('Kirill', 'email2@gmail.com', 0, (select id from departments where deparment_name='HR')),
('Jenya', 'email3@gmail.com', 0, (select id from departments where deparment_name='Security')),
('Sanya', 'email4@gmail.com', 0, (select id from departments where deparment_name='IT')),
('Petya', 'email5@gmail.com', 0, (select id from departments where deparment_name='HR')),
('Masha', 'email6@gmail.com', 0, (select id from departments where deparment_name='Security')),
('Dasha', 'email7@gmail.com', 0, (select id from departments where deparment_name='IT')),
('Artyom', 'email8@gmail.com', 0, (select id from departments where deparment_name='HR')),
('Ura', 'email9@gmail.com', 0, (select id from departments where deparment_name='Security')),
('Katya', 'email10@gmail.com', 0, (select id from departments where deparment_name='IT')),
('Kostya', 'email11@gmail.com', 0, (select id from departments where deparment_name='HR')),
('Nikita', 'email12@gmail.com', 0, (select id from departments where deparment_name='Security')),
('Yaric', 'email13@gmail.com', 0, (select id from departments where deparment_name='IT')),
('Gena', 'email14@gmail.com', 0, (select id from departments where deparment_name='HR'));