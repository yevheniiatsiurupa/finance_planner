--liquibase formatted sql

--changeSet etsiurupa:short_term_plan-01
alter table short_term_plan
add currency_id integer not null default 1,
add constraint fk_currency_id
foreign key (currency_id)
references currencies(id);