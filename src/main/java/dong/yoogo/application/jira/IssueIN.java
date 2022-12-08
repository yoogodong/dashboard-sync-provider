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
        final int count = repository.historyCount(id);

        final Issue.IssueBuilder issueBuilder = Issue.builder().id(id).issueKey(key)
                .changelogSize(changelog.total)
                .histories(changelog.toHistoryList(count));
        return fields.toIssue(issueBuilder).build();
    }
}
