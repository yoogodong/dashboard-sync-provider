package dong.yoogo.application.jira;

import dong.yoogo.domain.jira.Issue;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * todo: 除了 issue 的其他类型是否也是这个模型？
 *
 */
@ToString(exclude = {"issues","names"})
@Data
public class ResultIN {
    private int startAt;
    private int total;
    private int maxResults;
    private List<IssueIN> issues;
    private Map<String,String> names;

    public boolean hasNext(){
       return  startAt+maxResults<total;
    }

    public List<Issue> getIssues(){
        return issues.parallelStream()
                .map(in->in.toIssue())
                .collect(Collectors.toList());
    }

}
