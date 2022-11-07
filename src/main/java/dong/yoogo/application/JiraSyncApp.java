package dong.yoogo.application;

import dong.yoogo.domain.jira.IssueRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class JiraSyncApp {
    private JiraClient jira;
    private IssueRepository repository;

    @Value("${provider.projects}")
    private List<String> projects;

    public void syncIssue2(){
        jira.pagedSync(resultIN -> repository.saveAll(resultIN.getIssues()));
    }


    /**
     *
     */
    public void syncIssue(){
        final List<String> pidList = evaluateProjects();
        pidList.parallelStream().forEach(pid-> jira.pagedSync(pid, resultIN -> repository.saveAll(resultIN.getIssues())));
    }
    /**
     * 评估要同步的项目列表
     */
    private List<String> evaluateProjects(){
        if (projects==null || projects.isEmpty()){
           projects = jira.getAllProjectsId();
        }
        return projects;
    }
}
