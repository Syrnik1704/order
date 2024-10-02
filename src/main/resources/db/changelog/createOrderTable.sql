CREATE TABLE orders
(
    id        serial primary key,
    uid       varchar not null,
    orders    varchar not null,
    status    varchar not null,
    firstName varchar not null,
    lastName  varchar not null,
    phone     varchar not null,
    email     varchar not null,
    city      varchar not null,
    street    varchar not null,
    number    varchar not null,
    postCode  varchar not null,
    deliver   integer REFERENCES "deliver" (id)
)
