insert into customer (id, address, firstname, lastname, postal_code, tel)
values (1, 'tehran', 'Abbas', 'Goodarzi', '1234567890', '02112345678');
insert into user (id, email, enabled, mobile, password, register_date, username, customer_id)
values (1, 'Abbas@gmail.com', 1, '09121234567', '78456', now(), 'admin', 1);