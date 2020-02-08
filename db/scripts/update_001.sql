create table currencies(
	id serial primary key,
	name varchar
);

insert into currencies (name)
values ('ruble'),
       ('dollar'),
       ('euro');

create table authorities(
	id serial primary key,
	name varchar
);

insert into authorities (name)
values ('ROLE_USER'),
       ('ROLE_ADMIN');

create table users(
	id serial primary key,
	username varchar(50) not null unique,
	password varchar (68) not null,
	enabled boolean,
	name varchar(100),
	email varchar,
	created date
);

insert into users (username, password, enabled, name, email, created)
values ('jane_ts', '{noop}123', true, 'Juju', 'jane@mail.ru', now()),
       ('alex_be', '{noop}123', true, 'Sanya', 'sanya@mail.ru', now()),
       ('admin', '{noop}456', true, 'Juju', 'admin@mail.ru', now()),
       ('default', '{noop}456', true, 'Default User', 'default@mail.ru', now());

create table user_authority(
	id serial primary key,
	user_id integer references users(id),
	authority_id integer references authorities(id)
);

insert into user_authority (user_id, authority_id)
values (1, 1),
       (2, 1),
       (3, 1),
       (3, 2),
       (4, 1);

create table user_config(
	id integer primary key references users(id),
	currency_id integer references currencies(id),
	expense_categories varchar,
	income_categories varchar
);

