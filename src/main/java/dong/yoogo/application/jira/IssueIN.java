package dong.yoogo.application.jira;

import dong.yoogo.domain.jira.Issue;
import lombok.Data;

@Data
public class IssueIN {
    long id;
    String key;
    FieldsIN fields;
    ChangelogIN changelog;

    public Issue toIssue() {
        return Issue.builder().id(id).issueKey(key)
                .created(fields.created)
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
