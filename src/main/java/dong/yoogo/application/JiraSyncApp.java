package dong.yoogo.application;

import dong.yoogo.domain.jira.Issue;
import dong.yoogo.domain.jira.IssueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Slf4j
@PropertySource("classpath:config/dashboard-sync-provider.properties")
public class JiraSyncApp {
    private final JiraClient jira;
    private final IssueRepository repository;

    @Value("${provider.projects}")
    private List<String> projConfig;

    @Value("${provider.updatedFrom}")
    private String updatedFrom;
    @Value("${issue.sync.per.milliseconds}")
    private long fixDelay;

    private final Map<String, String> proj_lastSync = new HashMap<>();

    public JiraSyncApp(JiraClient jira, IssueRepository repository) {
        this.jira = jira;
        this.repository = repository;
    }

    /**
     * synchronize issues for specified project list and specified time point
     */
    @Scheduled(fixedDelayString = "${issue.sync.per.milliseconds}")
    public void syncIssue() {
        final List<String> pks = evaluateProjects();
        log.info("将同步以下 {} 个项目的 issue : {}", pks.size(), pks);
        final Instant start = Instant.now();
        final int[] count = new int[1];
        pks.parallelStream().forEach(pk -> {
            final String lastSync = proj_lastSync.get(pk);
            String lastUpdated = lastSync == null ? queryLastUpdatedOf(pk) : lastSync;
            log.info("项目{}将获取 {} 之后更新的数据", pk, lastUpdated);
            syncProjectFrom(count, pk, lastUpdated);
            final String serverTime = jira.jiraServerTime().minusMinutes(2).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            proj_lastSync.put(pk, serverTime);
            log.info("项目{}下次的同步起点：{}", pk, serverTime);
        });
        log.info("已经完成本轮数据同步, 更新了 {} issue,耗时 {},下次同步在 {} 分钟后",
                count[0], Duration.between(start, Instant.now()), fixDelay / 60000);
    }

    private void syncProjectFrom(int[] count, String pk, String lastUpdated) {
        jira.queryIssuesOfProject(pk, lastUpdated, resultIN -> {
            log.info("{} 保存结果 {}", pk, resultIN);
            final List<Issue> issues = resultIN.getIssues();
            count[0] += issues.size();
            repository.deleteAllInBatch(issues);
            repository.saveAll(issues);
            log.info("{} 已经保存 {}", pk, resultIN);
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
        log.info("项目 {} 最后的同步时间 {}", projectKey, zonedDateTime == null ? "不存在" : zonedDateTime);
        if (zonedDateTime == null) return updatedFrom;
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.CHINA));
    }


    /**
     * 评估要同步的项目列表
     */
    private List<String> evaluateProjects() {
        return projConfig.isEmpty() ? jira.projectKeyList() : projConfig;
    }
}
