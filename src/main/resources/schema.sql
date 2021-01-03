create table categories (
    category_id bigint not null auto_increment,
    name varchar(255) not null,
    primary key (category_id)
);

create table journals (
    journal_id bigint not null auto_increment,
    title1 varchar(255) not null,
    issn1 varchar(255) not null,
    eissn1 varchar(255) not null,
    title2 varchar(255),
    issn2 varchar(255),
    eissn2 varchar(255),
    points int,
    primary key (journal_id)
);

create table users (
    user_id bigint not null auto_increment,
    email varchar(255) not null unique,
    password varchar(255) not null,
    name varchar(255) not null,
    surname varchar(255) not null,
    enabled tinyint not null default 1,
--     primary key (email),
    primary key (user_id)
);

create table groups (
    group_id bigint not null auto_increment,
    name varchar(255) not null,
    owner_user_id bigint not null,
    foreign key (owner_user_id) references users(user_id),
    primary key (group_id)
);

create table authorities (
    auth_id bigint not null auto_increment,
    email varchar(255) not null,
    authority varchar(255) not null,
    foreign key (email) references users(email)
);

create unique index ix_auth_email
on authorities (email, authority);

create table journals_categories (
    journals_journal_id bigint not null,
    categories_category_id bigint not null,
    foreign key (categories_category_id) references categories(category_id),
    foreign key (journals_journal_id) references journals(journal_id),
    unique (journals_journal_id, categories_category_id)
);

create table groups_journals(
    groups_group_id bigint not null,
    journals_journal_id bigint not null,
    foreign key (groups_group_id) references groups(group_id),
    foreign key (journals_journal_id) references journals(journal_id),
    unique (groups_group_id, journals_journal_id)
);

create table groups_users(
    groups_group_id bigint not null,
    users_user_id bigint not null,
    foreign key (groups_group_id) references groups(group_id),
    foreign key (users_user_id) references users(user_id),
    unique (groups_group_id, users_user_id)
);
