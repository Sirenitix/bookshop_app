INSERT INTO books(author,title,size) VALUES ('To Kill a Mockingbird','Harper Lee',336);
INSERT INTO books(author,title,size) VALUES ('Laura Dave','The Last Thing He Told Me',320);
INSERT INTO books(author,title,size) VALUES ('The Bell Jar','Sylvia Plath',244);
INSERT INTO books(author,title,size) VALUES ('On Writing Well','William Zinsser',336);
INSERT INTO books(author,title,size) VALUES ('Writing About Your Life','William Zinsser',240);

insert into users (username, password, enabled) values ('bob', '{noop}123', true);
insert into authorities (username, authority) values ('bob', 'ROLE_USER');

insert into users (username, password, enabled) values ('sara', '{noop}234', true);
insert into authorities (username, authority) values ('sara', 'ROLE_ADMIN');

