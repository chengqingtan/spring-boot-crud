#建数据库和见表语句

drop database if exists pet_transaction;


create
    database if not exists pet_transaction;

use
    pet_transaction;

drop table if exists tb_pet;
drop table if exists tb_user;


create table tb_user
(
    username    varchar(10) primary key,
    password    varchar(10) not null,
    private_key varchar(50),
    public_key  varchar(50),
    address     varchar(50)
);

create table tb_pet
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

# #tb_user 操作
# select *
# from tb_user;
#
# select *
# from tb_user where username='张三';
#
# insert into tb_user (username, password, private_key, public_key, address)
# VALUES ('张三', 'passwd', '3sdxd0x', 'xdsf3', '0x325af');
#
# #tb_pet 操作
# select *
# from tb_pet;
#
# select * from tb_pet where pet_id=1;
# insert into tb_pet (pet_name, owner, picture_path, description, price, pet_class) VALUES ();
#
# update tb_pet set has_sold_out=true where pet_id=1;

