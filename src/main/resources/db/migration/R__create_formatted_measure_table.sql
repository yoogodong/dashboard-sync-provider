create table formatted_measure
(
    id      bigint primary key auto_increment,
    project varchar(30),
    metric  varchar(30),
    updated date,
    value   varchar(30)
);
