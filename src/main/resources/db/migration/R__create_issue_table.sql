-- issue 表， 字段有增加，表应该重建，才能重新获取历史数据，所以采用 R__ 的模式
drop table if exists issue;
create table issue
(
    id                integer not null,
    changelog_size    integer,
    component_id      integer,
    component_name    varchar(20),
    created           datetime(6),
    fix_version_id    integer,
    fix_version_name  varchar(20),
    issue_key         varchar(20) unique,
    issue_type_id     integer,
    issue_type_name   varchar(20),
    parent_id         integer,
    project_id        integer,
    project_key       varchar(20),
    project_name      varchar(50),
    status_id         integer,
    status_name       varchar(50),
    summary           varchar(100),
    updated           datetime(6),
    labels            varchar(100),
-- 自定义字段
    bug_type_value    varchar(200),
-- 下面是jira 中时间相关的自定义字段
    req_start0        date,
    req_end0          date,
    dev_start0        date,
    dev_end0          date,
    sit_start0        date,
    sit_end0          date,
    inner_test_start0 date,
    inner_test_end0   date,
    uat_start0        date,
    uat_end0          date,
    pub0              date,
    req_start1        date,
    req_end1          date,
    dev_start1        date,
    dev_end1          date,
    sit_start1        date,
    sit_end1          date,
    inner_test_start1 date,
    inner_test_end1   date,
    uat_start1        date,
    uat_end1          date,
    pub1              date,
    primary key (id)
) engine = InnoDB;



