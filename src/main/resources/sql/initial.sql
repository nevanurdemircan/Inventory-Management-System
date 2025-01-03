create table photo
(
    id         int auto_increment
        primary key,
    effect_id  int          null,
    table_name varchar(255) null
);

create table product
(
    name     varchar(255) null,
    quantity int          null,
    price    double       null,
    discount double       null,
    id       int auto_increment
        primary key
);

create table billed_product
(
    id            int auto_increment
        primary key,
    product_id    int    null,
    current_price double null,
    amount        double null,
    total_price   double null,
    constraint billed_product_product_id_fk
        foreign key (product_id) references product (id)
);

create table users
(
    id       int auto_increment
        primary key,
    type     int          null,
    name     varchar(255) null,
    email    varchar(255) null,
    password varchar(255) null,
    phone    varchar(255) null
);

create table bill
(
    id          int auto_increment
        primary key,
    supplier_id int    not null,
    retailer_id int    not null,
    totalPrice  double null,
    date        date   null,
    constraint bill_users_id_fk
        foreign key (supplier_id) references users (id),
    constraint bill_users_id_fk2
        foreign key (retailer_id) references users (id)
);

