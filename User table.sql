drop table if exists user;
create table user (
    id                        bigint not null auto_increment,
    username                      varchar(255) not null,
    password                   varchar(255) not null,

    constraint pk_user primary key (id))
	;

insert into user (id,username,password) values (  1,'admin', '$2y$10$3HB31T86lvMAoUTLi07dC.zks3XOu1NR4Djfb8D59PpNYcD4AJBLK');
