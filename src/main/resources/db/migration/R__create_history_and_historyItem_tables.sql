--  这两个表， 字段有增加，两个表都应该重建，才能重新获取历史数据，所以采用 R__ 的模式
drop table if exists history;
create table history
(
    id                  INTEGER not null,
    author_display_name varchar(100),
    author_key          varchar(50),
    created             datetime(6),
    issue_id            int,
    primary key (id)
) engine = InnoDB;

drop table if exists history_item;
create table history_item
(
    id          BIGINT not null primary key auto_increment,
    history_id  INTEGER not null,
    field       varchar(30),
    field_type  varchar(20),
    from_string varchar(20),
    from_value  varchar(20),
    to_string   varchar(20),
    to_value    varchar(20)
) engine = InnoDB;




