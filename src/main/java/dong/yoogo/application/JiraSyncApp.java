package dong.yoogo.application;

import dong.yoogo.domain.jira.IssueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@PropertySource("classpath:config/dashboard-sync-provider.properties")
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
        final List<String> pks = evaluateProjects();
        log.info("将同步以下项目的 issue : {}",pks);
        pks.parallelStream().forEach(projectKey -> {
            String lastUpdated = queryLastUpdatedOf(projectKey);
            log.info("项目{}上次的同步时间：{}",projectKey,lastUpdated);
            jira.queryIssuesOfProject(projectKey, lastUpdated, resultIN -> repository.saveAll(resultIN.getIssues()));
        });
    }

    /**
     * get the last updated time for specified project
     *
     * @param projectKey project key
     * @return the last updated time
     */
    private String queryLastUpdatedOf(String projectKey) {
        ZonedDateTime zonedDateTime = repository.queryLastUpdatedOf(projectKey);
        if (zonedDateTime == null) return updatedFrom;
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


    /**
     * 评估要同步的项目列表
     */
    private List<String> evaluateProjects() {
        if (projects == null || projects.isEmpty()) {
            projects = jira.projectKeyList();
        }
        return projects;
    }
}
