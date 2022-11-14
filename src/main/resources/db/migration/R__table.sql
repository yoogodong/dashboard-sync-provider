-- 这三个表，有一个修改， 其他两个都应该重建，所以放在一起
drop table if exists issue;
create table issue
(
    id               bigint not null,
    changelog_size   integer,
    component_id     integer,
    component_name   varchar(20),
    created          datetime(6),
    fix_version_id   integer,
    fix_version_name varchar(20),
    issue_key        varchar(20),
    issue_type_id    integer,
    issue_type_name  varchar(20),
    project_id       integer,
    project_key      varchar(20),
    project_name     varchar(50),
    status_id        integer,
    status_name      varchar(50),
    updated          datetime(6),
    primary key (id)
) engine = InnoDB;


drop table if exists history;
create table history
(
    id                  bigint not null,
    author_display_name varchar(100),
    author_key          varchar(50),
    created             datetime(6),
    issue_id            bigint,
    primary key (id),
    FOREIGN KEY (issue_id)
        REFERENCES issue (id)
        ON DELETE CASCADE
) engine = InnoDB;

drop table if exists history_items;
create table history_items
(
    history_id  bigint not null,
    field       varchar(30),
    field_type  varchar(20),
    from_string varchar(20),
    from_value  varchar(20),
    to_string   varchar(20),
    to_value    varchar(20),
    foreign key (history_id)
        REFERENCES history (id)
        ON DELETE CASCADE
) engine = InnoDB;




