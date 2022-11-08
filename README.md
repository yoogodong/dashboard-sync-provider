#为什么存在？
- grafana 虽然有 jira 数据源插件，但是收费
- 为了使用集中看板， 要从包括 jira 的多种管理系统中获取数据


#设计
###要求
- 管理数据库视图的脚本

###原则
- 最简配置，最简维护
- 保持数据源中，原始的数据结构

#变更
### 扩展字段
- 在 JiraClientImple 过滤的 fields 中添加当前字段, 要与json 中的属性名相同
- 在 Issue 中添加字段
- 在 IssueIN 及其关联的成员中映射json 模型， 并转化成 Issue 的字段
- 在建表 DDL 中添加字段

