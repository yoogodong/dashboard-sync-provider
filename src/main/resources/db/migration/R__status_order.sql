-- issue 的状态顺序
drop table if exists status_order;
create table status_order
(
    issue_type_id integer,
    status_id     integer,
    order_id      integer,
    primary key (issue_type_id, status_id)
);

-- 状态顺序
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 10416, 1);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 11701, 2);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 11703, 3);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 11705, 4);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 11706, 5);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 10414, 6);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 10101, 7);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 10100, 8);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 10219, 9);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 3, 10);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 10213, 11);
insert into status_order(issue_type_id, status_id, order_id)
VALUES (12400, 10214, 12);
