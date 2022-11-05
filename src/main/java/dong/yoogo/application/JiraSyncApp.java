package dong.yoogo.application;

import dong.yoogo.application.jira.ResultIN;
import dong.yoogo.domain.jira.IssueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class JiraSyncApp {
    private JiraClient jira;
    private IssueRepository repository;

    public void syncIssue(){
        syncIssue(0,3);
    }

    private void syncIssue(int startAt, int maxResults){
        final ResultIN result = jira.getIssues(startAt,maxResults);
        log.debug("jira result: {}",result);
        repository.saveAll(result.getIssues());
        if (result.hasNext()){
            syncIssue(startAt+maxResults, maxResults);
        }
    }
}
