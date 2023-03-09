create table item
(
    id           serial primary key,
    name         varchar(255),
    description  varchar(255),
    dollar_price double
);

create table users
(
    id                 serial primary key,
    name               varchar(255),
    preferred_currency varchar(255)
);