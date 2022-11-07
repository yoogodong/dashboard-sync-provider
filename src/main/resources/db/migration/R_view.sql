-- 统计两个状态时长
drop view if exists duration;
create view duration as
select a.issue_key,
       a.created                                   as open_time,
       b.created                                   as review_time,
       timestampdiff(second, a.created, b.created) as seconds
from (select i.issue_key, h.created
      from issue i
               join history h on i.id = h.issue_id
               join history_items hi on h.id = hi.history_id
      where field = 'status'
        and to_string = 'Open') as a
         join
     (select i.issue_key, max(h.created) as created
      from issue i
               join history h on i.id = h.issue_id
               join history_items hi on h.id = hi.history_id
      where field = 'status'
        and to_string = 'Reviewing'
      group by i.issue_key, to_string) as b
     on a.issue_key = b.issue_key
