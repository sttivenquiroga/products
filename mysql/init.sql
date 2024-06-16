create table categories
(
    id            int auto_increment
        primary key,
    category_name varchar(50) not null
);

create table products
(
    category_id  int          null,
    id           int auto_increment
        primary key,
    quantity     int          not null,
    product_name varchar(50)  not null,
    status       varchar(255) null,
    constraint FKog2rp4qthbtt2lfyhfo32lsw9
        foreign key (category_id) references categories (id)
);

create table products_seq
(
    next_val bigint null
);

create table roles
(
    id   int auto_increment
        primary key,
    name enum ('ADMIN', 'USER') not null
);

create table users
(
    id        int auto_increment
        primary key,
    firstname varchar(50)  not null,
    lastname  varchar(50)  not null,
    username  varchar(50)  not null,
    password  varchar(255) not null,
    role_id   varchar(255) null
);

create table users_roles
(
    roles_id int not null,
    user_id  int not null,
    primary key (roles_id, user_id),
    constraint FK2o0jvgh89lemvvo17cbqvdxaa
        foreign key (user_id) references users (id),
    constraint FKa62j07k5mhgifpp955h37ponj
        foreign key (roles_id) references roles (id)
);

insert into users(username, firstname, lastname, password, role_id) values('usertest','Juan', 'Perez', '$2a$10$sSORJN.6leRpR4pAjlYQoOCxAUHJV2fUBrFNbY.5VU1egZVlCMZEi', '1');
insert into users(username, firstname, lastname, password, role_id) values('prueba','Homer', 'Simpson', '$2a$10$uqIgrPz53FDSYfz1AIqIhebWEzt40ZSYHxsvFZDlrizUqC8Vl8ys6', '2');

insert into roles(name) values('ADMIN');
insert into roles(name) values('USER');

insert into users_roles(roles_id, user_id) value (1,1);
insert into users_roles(roles_id, user_id) value (2,1);
insert into users_roles(roles_id, user_id) value (2,2);
