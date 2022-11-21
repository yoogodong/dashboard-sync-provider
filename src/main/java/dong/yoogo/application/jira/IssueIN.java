package dong.yoogo.application.jira;

import dong.yoogo.domain.jira.Issue;
import dong.yoogo.domain.jira.IssueRepository;
import lombok.Data;

@Data
public class IssueIN {
    Integer id;
    ChangelogIN changelog;
    FieldsIN fields;
    String key;


    public Issue toIssue(IssueRepository repository) {
        fields.postConstruct();
        final int count = repository.historyCount(id);

        return Issue.builder().id(id).issueKey(key)
                .changelogSize(changelog.total)
                .componentId(fields.getComponentId())
                .componentName(fields.getComponentName())
                .created(fields.created)
                .fixVersionId(fields.getFixVersionId())
                .fixVersionName(fields.getFixVersionName())
                .issueTypeId(fields.issueType.id)
                .issueTypeName(fields.issueType.name)
                .parentId(fields.parent==null?null:fields.parent.id)
                .projectId(fields.project.id)
                .projectKey(fields.project.key)
                .projectName(fields.project.name)
                .statusId(fields.status.id)
                .statusName(fields.status.name)
                .updated(fields.updated)
                .histories(changelog.toHistoryList(count))
                .build();
    }
}
