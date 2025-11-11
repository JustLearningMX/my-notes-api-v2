create table users (
    id bigint not null auto_increment,
    first_name varchar(255) NOT NULL,
    last_name varchar(255) NOT NULL,
    email varchar(255) NOT NULL unique,
    password varchar(255) NOT NULL,
    active boolean NOT NULL DEFAULT true,
    role varchar(50) NOT NULL DEFAULT 'USER',
    primary key (id)
);