package dong.yoogo.application.jira;

import dong.yoogo.domain.jira.Issue;
import lombok.Data;

@Data
public class IssueIN {
    Long id;
    ChangelogIN changelog;
    FieldsIN fields;
    String key;


    public Issue toIssue() {
        fields.postConstruct();

        return Issue.builder().id(id).issueKey(key)
                .changelogSize(changelog.total)
                .componentId(fields.getComponentId())
                .componentName(fields.getComponentName())
                .created(fields.created)
                .fixVersionId(fields.getFixVersionId())
                .fixVersionName(fields.getFixVersionName())
                .issueTypeId(fields.issueType.id)
                .issueTypeName(fields.issueType.name)
                .projectId(fields.project.id)
                .projectKey(fields.project.key)
                .projectName(fields.project.name)
                .statusId(fields.status.id)
                .statusName(fields.status.name)
                .updated(fields.updated)
                .histories(changelog.toHistoryList())
                .build();
    }
}
