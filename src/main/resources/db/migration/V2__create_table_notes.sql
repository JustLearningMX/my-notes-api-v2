create table notes (
    id bigint not null auto_increment,
    title varchar(255) not null,
    description varchar(500),
    last_edited DATE not null,
    state varchar(255) not null,
    deleted boolean not null default false,
    primary key (id)
);