create database bank_ms;

create table users (
id int not null primary key auto_increment,
username varchar(32),
password varchar(32),
created timestamp,
is_active boolean
);