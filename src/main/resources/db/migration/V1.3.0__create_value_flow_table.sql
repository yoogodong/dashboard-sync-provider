-- issue 的价值流顺序
create table value_flow
(
    issue_type_id integer,
    status_id     integer,
    order_id      integer,
    primary key (issue_type_id, status_id)
);