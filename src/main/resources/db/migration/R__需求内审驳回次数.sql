-- 内审驳回次数
drop view if exists inner_approval_rework;
create view inner_approval_rework as
select i.issue_key, count(h.id) as times
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where i.issue_type_name = '常规需求开发'
  and i.project_key = 'ETONG'
  and hi.field = 'status'
  and hi.from_string = '待复核'
  and hi.to_string = '需求分析'
group by i.issue_key
union
select i.issue_key, count(h.id) as times
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where i.issue_type_name = '需求任务'
  and i.project_key = 'ETONG'
  and hi.field = 'status'
  and hi.from_string = '需求内审中'
  and hi.to_string = '需求待分析'
group by i.issue_key
