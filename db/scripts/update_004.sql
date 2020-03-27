create table short_term_plan(
	id serial primary key,
	comment varchar,
	name varchar,
	start_date date,
	end_date date,
	user_account_id integer references users(id)
);

insert into short_term_plan(comment, name, start_date, end_date, user_account_id)
values ('plan test','name test', '2020-01-01', '2020-01-31', 1);

alter table expenses_planned
add short_term_plan_id integer not null default 1,
add constraint fk_short_plan
foreign key (short_term_plan_id)
references short_term_plan(id);

create table incomes_planned(
	id serial primary key,
	amount integer,
	comment varchar,
	category_name varchar,
	sub_category_name varchar,
	created date,
	currency_id integer references currencies(id),
	user_account_id integer references users(id),
	short_term_plan_id integer references short_term_plan(id)
);

insert into incomes_planned(amount, comment, category_name, sub_category_name, created, currency_id, user_account_id, short_term_plan_id)
values (40000,'', 'Доп. доход', 'Разное', '2020-01-01', 1, 1, 1),
       (13500,'', 'Работа', 'Зарплата моя', '2020-01-01', 1, 1, 1),
       (25500,'', 'Работа', 'Зарплата муж', '2020-01-01', 1, 1, 1),
       (1500,'', 'Работа', 'Английский', '2020-01-01', 1, 1, 1);