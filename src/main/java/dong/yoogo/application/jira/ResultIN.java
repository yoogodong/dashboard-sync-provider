package dong.yoogo.application.jira;

import dong.yoogo.domain.jira.Issue;
import dong.yoogo.domain.jira.IssueRepository;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * todo: 除了 issue 的其他类型是否也是这个模型？
 */
@Slf4j
@ToString(exclude = {"issues", "names"})
@Data
public class ResultIN {
    private int startAt;
    private int total;
    private int maxResults;
    private List<IssueIN> issues;
    private Map<String, String> names;

    public boolean hasNext() {
        return startAt + maxResults < total;
    }

    public List<Issue> getIssues(IssueRepository repository) {
        return issues.stream()
                .map(issueIN -> {
                    try {
                        return issueIN.toIssue(repository);
                    } catch (Exception e) {
                        log.error("Issue 格式有异常,错误信息 {}, Issue key {}",e, issueIN.key);
                        return Issue.builder().id(issueIN.id).issueKey(issueIN.key).build();
                    }
                }).collect(Collectors.toList());
    }
}
