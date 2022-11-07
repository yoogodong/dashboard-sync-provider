package dong.yoogo.application;

import dong.yoogo.domain.jira.IssueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class JiraSyncApp {
    @Autowired
    private JiraClient jira;
    @Autowired
    private IssueRepository repository;

    @Value("${provider.projects}")
    private List<String> projects;

    @Value("${provider.updatedFrom}")
    private String updatedFrom;

    public void syncIssue2() {
        jira.pagedSync(resultIN -> repository.saveAll(resultIN.getIssues()));
    }


    /**
     *
     */
    public void syncIssue3() {
        final List<String> pidList = evaluateProjects();
        pidList.parallelStream().forEach(pid -> jira.pagedSync(pid, resultIN -> repository.saveAll(resultIN.getIssues())));
    }


    /**
     *
     */
    public void syncIssue() {
        final List<String> pidList = evaluateProjects();
        pidList.parallelStream().forEach(pid -> {
            String lastUpdated = queryLastUpdatedOf(pid);
            jira.pagedSync(pid,lastUpdated, resultIN -> repository.saveAll(resultIN.getIssues()));
        });
    }

    /**
     * 获取项目的最后更新时间
     * @param pid  项目 id
     * @return
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
