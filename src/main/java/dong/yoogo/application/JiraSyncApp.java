package dong.yoogo.application;

import dong.yoogo.domain.jira.IssueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class JiraSyncApp {
    private final JiraClient jira;
    private final IssueRepository repository;

    @Value("${provider.projects}")
    private List<String> projects;

    @Value("${provider.updatedFrom}")
    private String updatedFrom;


    public JiraSyncApp(JiraClient jira, IssueRepository repository) {
        this.jira = jira;
        this.repository = repository;
    }

    /**
     * synchronize issues for specified project list and specified time point
     */
    public void syncIssue() {
        final List<String> pidList = evaluateProjects();
        pidList.parallelStream().forEach(pid -> {
            String lastUpdated = queryLastUpdatedOf(pid);
            jira.queryIssuesOfProject(pid, lastUpdated, resultIN -> repository.saveAll(resultIN.getIssues()));
        });
    }

    /**
     * get the last updated time for specified project
     *
     * @param pid project id
     * @return the last updated time
     */
    private String queryLastUpdatedOf(String pid) {
        ZonedDateTime zonedDateTime = repository.queryLastUpdatedOf(Integer.valueOf(pid));
        if (zonedDateTime == null) return updatedFrom;
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


    /**
     * 评估要同步的项目列表
     */
    private List<String> evaluateProjects() {
        if (projects == null || projects.isEmpty()) {
            projects = jira.getAllProjectsId();
        }
        return projects;
    }
}
