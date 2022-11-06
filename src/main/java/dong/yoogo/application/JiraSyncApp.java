package dong.yoogo.application;

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
        jira.pagedSync(resultIN -> repository.saveAll(resultIN.getIssues()));
    }
}
