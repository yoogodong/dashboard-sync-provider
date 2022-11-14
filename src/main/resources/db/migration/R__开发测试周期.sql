-- 开发测试周期，三个视图
-- 常规需求开发
drop view if exists dev_sit_duration_normal;
create view dev_sit_duration_normal as
select i.issue_key,
       min(h1.created)                               as pre_dev,
       max(h2.created)                               as post_sit,
       timestampdiff(MINUTE, h1.created, h2.created) as minutes,
       i.component_name,
       i.fix_version_name,
       i.project_key
from issue i
         join history h1 on i.id = h1.issue_id
         join history_items hi1 on h1.id = hi1.history_id
         join history h2 on i.id = h2.issue_id
         join history_items hi2 on h2.id = hi2.history_id
where i.issue_type_name = '常规需求开发'
  and hi1.field = 'status'
  and hi1.to_string = '开发中'
  and hi2.field = 'status'
  and hi2.to_string = 'UAT测试'
group by i.issue_key, minutes, i.component_name, i.fix_version_name, i.project_key
order by i.issue_key;


-- 简易需求开发
drop view if exists dev_sit_duration_simple;
create view dev_sit_duration_simple as
select i.issue_key,
       min(h1.created)                               as pre_dev,
       max(h2.created)                               as post_sit,
       timestampdiff(MINUTE, h1.created, h2.created) as minutes,
       i.component_name,
       i.fix_version_name,
       i.project_key
from issue i
         join history h1 on i.id = h1.issue_id
         join history_items hi1 on h1.id = hi1.history_id
         join history h2 on i.id = h2.issue_id
         join history_items hi2 on h2.id = hi2.history_id
where i.issue_type_name = '简易需求开发'
  and hi1.field = 'status'
  and hi1.to_string = '开发中'
  and hi2.field = 'status'
  and hi2.to_string = '待上线'
group by i.issue_key, minutes, i.component_name, i.fix_version_name, i.project_key
order by i.issue_key;


-- 需求任务
drop view if exists dev_sit_duration_req_task;
create view dev_sit_duration_req_task as
select i.issue_key,
       min(h1.created)                               as pre_dev,
       max(h2.created)                               as post_sit,
       timestampdiff(MINUTE, h1.created, h2.created) as minutes,
       i.component_name,
       i.fix_version_name,
       i.project_key
from issue i
         join history h1 on i.id = h1.issue_id
         join history_items hi1 on h1.id = hi1.history_id
         join history h2 on i.id = h2.issue_id
         join history_items hi2 on h2.id = hi2.history_id
where i.issue_type_name = '需求任务'
  and hi1.field = 'status'
  and hi1.to_string = '开发中'
  and hi2.field = 'status'
  and hi2.to_string = 'UAT测试'
group by i.issue_key, minutes, i.component_name, i.fix_version_name, i.project_key
order by i.issue_key