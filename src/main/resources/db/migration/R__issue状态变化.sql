-- 所有类型的 issue 通用的状态改变记录
drop view if exists issue_status_log;
create view issue_status_log as
select i.id,
       i.issue_type_id,
       i.issue_type_name,
       i.component_id,
       i.component_name,
       i.created as born,
       i.fix_version_id,
       i.fix_version_name,
       i.project_id,
       i.project_key,
       h.created,
       h.author_display_name,
       h.author_key,
       hi.from_value,
       hi.from_string,
       hi.to_value,
       hi.to_string
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where hi.field = 'status';




