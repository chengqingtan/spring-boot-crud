drop table if exists tb_pet;
drop table if exists tb_user_profile;
drop table if exists tb_user;


create table if not exists tb_user
(
    username    varchar(10) primary key,
    password    varchar(10) not null,
    private_key text,
    public_key  text,
    address     varchar(50)
);

create table if not exists tb_user_profile
(
    username varchar(10) primary key,
    birthday date,
    sex varchar(10) default 'hide' CHECK ( sex in ('male', 'female', 'hide') ),
    avatar_path varchar(50),
    hobby text,
    CONSTRAINT username_constraint
    FOREIGN KEY (username) REFERENCES tb_user (username)
);

create table if not exists tb_pet
(
    pet_id       int primary key auto_increment,
    pet_name     varchar(10),
    owner        varchar(10),
    picture_path varchar(50),
    description  varchar(20),
    price        int,
    pet_class    varchar(10),
    has_sold_out boolean default false,
    CONSTRAINT owner_constraint
        FOREIGN KEY (owner) REFERENCES tb_user (username)
);