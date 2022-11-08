drop view if exists issue_status_log;
create view issue_status_log as
select i.id,
       i.issue_type_name,
       i.created as born,
       h.created,
       hi.from_string,
       hi.to_string
from issue i
         join history h on i.id = h.issue_id
         join history_items hi on h.id = hi.history_id
where hi.field = 'status'