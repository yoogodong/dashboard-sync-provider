-- sonar measure history
create table measure(
  id    bigint primary key auto_increment,
  metric    varchar(30),
  updated   datetime(6),
  value     varchar(30)
);



