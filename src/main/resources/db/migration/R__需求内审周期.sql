-- 需求内审时长
drop view if exists inner_approval_duration;
create view inner_approval_duration as
(
select i.id,
       i.created                                   as born,
       h.created                                   as in_cmplt,
       timestampdiff(MINUTE, i.created, h.created) as minutes,
       i.fix_version_name,
       i.project_name,
       i.component_name
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where issue_type_name = '常规需求开发'
  and hi.field = 'status'
  and hi.to_string = 'UI设计'

union
select i.id,
       i.created                                   as born,
       h.created                                   as in_cmplt,
       timestampdiff(MINUTE, i.created, h.created) as minutes,
       i.fix_version_name,
       i.project_name,
       i.component_name
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where issue_type_name = '简易需求开发'
  and hi.field = 'status'
  and hi.to_string = '待开发'

union
select i.id,
       i.created                                   as born,
       h.created                                   as in_cmplt,
       timestampdiff(MINUTE, i.created, h.created) as minutes,
       i.fix_version_name,
       i.project_name,
       i.component_name
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where issue_type_name = '需求任务'
  and hi.field = 'status'
  and hi.to_string = '需求内审完成'
    );


-- 内审驳回次数
drop view if exists inner_approval_rework;
create view inner_approval_rework as
select i.id, count(h.id) as times
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where i.issue_type_name = '常规需求开发'
  and i.project_key = 'ETONG'
  and hi.field = 'status'
  and hi.from_string = '待复核'
  and hi.to_string = '需求分析'
group by i.id
union
select i.id, count(h.id) as times
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where i.issue_type_name = '需求任务'
  and i.project_key = 'ETONG'
  and hi.field = 'status'
  and hi.from_string = '需求内审中'
  and hi.to_string = '需求待分析'
group by i.id



