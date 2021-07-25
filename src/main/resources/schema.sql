DROP TABLE IF EXISTS books;

CREATE TABLE books(
id INT AUTO_INCREMENT PRIMARY KEY,
author VARCHAR(250) NOT NULL,
title VARCHAR(250) NOT NULL,
size INT DEFAULT NULL
);

DROP TABLE IF EXISTS docs;

CREATE TABLE docs(
   id INT AUTO_INCREMENT PRIMARY KEY,
   fileName VARCHAR(250) NOT NULL,
   file BLOB
);

DROP TABLE if exists users;
create table users(
                      username varchar_ignorecase(50) not null primary key,
                      password varchar_ignorecase(200) not null,
                      enabled boolean not null
);

DROP TABLE if exists authorities;
create table authorities (
                             username varchar_ignorecase(50) not null,
                             authority varchar_ignorecase(50) not null,
                             constraint fk_authorities_users foreign key(username) references users(username)
);