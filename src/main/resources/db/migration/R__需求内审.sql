-- 需求内审时长
drop view if exists inner_approval;
create view inner_approval as
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
  and hi.to_string = '需求内审完成';


