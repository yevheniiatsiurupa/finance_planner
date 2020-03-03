create table expenses(
	id serial primary key,
	amount integer,
	comment varchar,
	category_name varchar,
	sub_category_name varchar,
	created date,
	currency_id integer references currencies(id),
	user_account_id integer references users(id),
	cache boolean
);

insert into expenses(amount, comment, category_name, sub_category_name, created, currency_id, user_account_id, cache)
values (1200,'', 'Еда / Напитки', 'Супермаркет', '2020-01-06', 1, 1, false),
       (4350,'', 'Еда / Напитки', 'Супермаркет', '2020-01-13', 1, 1, false),
       (4450,'', 'Еда / Напитки', 'Супермаркет', '2020-01-20', 1, 1, false),
       (4450,'', 'Еда / Напитки', 'Супермаркет', '2020-01-27', 1, 1, false),
       (600,'сухофрукты', 'Покупка товаров', 'НЗ', '2020-01-01', 1, 1, true),
       (900,'косметика', 'Покупка товаров', 'НЗ', '2020-01-01', 1, 1, false),
       (850,'хозтовары', 'Покупка товаров', 'НЗ', '2020-01-01', 1, 1, false),
       (250,'аптека', 'Покупка товаров', 'НЗ', '2020-01-01', 1, 1, false),
       (300,'свечи', 'Покупка товаров', 'НЗ', '2020-01-01', 1, 1, true),
       (2550,'', 'Здоровье / Спорт', 'Спорт', '2020-01-01', 1, 1, false ),
       (5150,'инвитро', 'Здоровье / Спорт', 'Медицина', '2020-01-01', 1, 1, false ),
       (1700,'аптека', 'Здоровье / Спорт', 'Медицина', '2020-01-01', 1, 1, false ),
       (3500,'поликлиника', 'Здоровье / Спорт', 'Медицина', '2020-01-01', 1, 1, false ),
       (4000,'', 'Транспорт', 'Метро', '2020-01-01', 1, 1, false ),
       (2300,'лошадки', 'Развлечения', 'Развлечения', '2020-01-01', 1, 1, false ),
       (500,'суши', 'Развлечения', 'Развлечения', '2020-01-01', 1, 1, false ),
       (300,'пицца', 'Развлечения', 'Развлечения', '2020-01-01', 1, 1, false ),
       (1200,'др', 'Разное', 'Разное', '2020-01-01', 1, 1, true ),
       (350,'стрижка', 'Разное', 'Разное', '2020-01-01', 1, 1, false ),
       (3500,'spring книга', 'Разное', 'Разное', '2020-01-01', 1, 1, false ),
       (37500,'ноутбук', 'Разное', 'Разное', '2020-01-01', 1, 1, false ),
       (2100,'леруа полки', 'Разное', 'Разное', '2020-01-01', 1, 1, false ),
       (600,'мтс', 'Квартира', 'Коммунальные', '2020-01-01', 1, 1, false ),
       (31000,'квартира', 'Квартира', 'Аренда квартиры', '2020-01-01', 1, 1, false );

create table expenses_planned(
	id serial primary key,
	amount integer,
	comment varchar,
	category_name varchar,
	sub_category_name varchar,
	created date,
	currency_id integer references currencies(id),
	user_account_id integer references users(id)
);

insert into expenses_planned(amount, comment, category_name, sub_category_name, created, currency_id, user_account_id)
values (14000,'', 'Еда / Напитки', 'Супермаркет', '2020-01-01', 1, 1),
       (5000,'', 'Покупка товаров', 'НЗ', '2020-01-01', 1, 1),
       (3600,'', 'Здоровье / Спорт', 'Спорт', '2020-01-01', 1, 1 ),
       (7000,'', 'Здоровье / Спорт', 'Медицина', '2020-01-01', 1, 1),
       (4000,'', 'Транспорт', 'Метро', '2020-01-01', 1, 1 ),
       (3000,'', 'Развлечения', 'Развлечения', '2020-01-01', 1, 1),
       (700,'мтс', 'Квартира', 'Коммунальные', '2020-01-01', 1, 1),
       (31000,'квартира', 'Квартира', 'Аренда квартиры', '2020-01-01', 1, 1);

create table incomes(
	id serial primary key,
	amount integer,
	comment varchar,
	category_name varchar,
	sub_category_name varchar,
	created date,
	currency_id integer references currencies(id),
	user_account_id integer references users(id)
);

insert into incomes(amount, comment, category_name, sub_category_name, created, currency_id, user_account_id)
values (41000,'', 'Доп. доход', 'Разное', '2020-01-01', 1, 1),
       (14500,'', 'Работа', 'Зарплата моя', '2020-01-01', 1, 1),
       (26500,'', 'Работа', 'Зарплата муж', '2020-01-01', 1, 1),
       (1600,'', 'Работа', 'Английский', '2020-01-01', 1, 1 );


