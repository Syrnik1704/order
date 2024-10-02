CREATE TABLE order_items
(
    id   serial primary key,
    uid varchar not null,
    name varchar not null,
    product varchar not null,
    priceunit decimal not null,
    pricesummary decimal not null,
    quantity bigint DEFAULT 1,
    orders bigint REFERENCES  orders(id)
);
