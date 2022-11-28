-- issue 的状态顺序
create table status_order
(
    issue_type_id integer,
    status_id     integer,
    order_id      integer,
    primary key (issue_type_id, status_id)
);