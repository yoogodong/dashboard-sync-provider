package dong.yoogo.infrastructure;

import dong.yoogo.application.sonarqube.MeasureResultIN;
import dong.yoogo.application.sonarqube.SonarQubeClient;
import dong.yoogo.domain.sonarqube.Measure;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

@Component
@AllArgsConstructor
@Slf4j
public class SonarQubeClientImpl implements SonarQubeClient {
    @Value("${sonarqube.sync.projects}")
    private final List<String> projects;
    @Value("${sonarqube.sync.metrics}")
    private final List<String> metrics;
    private final RestTemplate sonarRest;

    @Override
    public List<String> projectsNeedToSync() {
        if (projects.isEmpty()){
            log.error("没有指定要同步的 SonarQube 项目列表");
            throw new RuntimeException("project list is empty");
        }
        log.info("将同步 SonarQube 以下项目 {}",projects);
        return projects;
    }

    @Override
    public List<String> metricsNeedToSync() {
        if (metrics.isEmpty()){
            log.error("没有指定要同步的 SonarQube 指标列表");
            throw new RuntimeException("metric list is empty");
        }
        log.info("将同步 SonarQube 以下指标 {}",metrics);
        return metrics;
    }

    @Override
    public void restGetMetric(String project, String metric, ZonedDateTime from, Consumer<List<Measure>> consumer) {
        restGetMetric(project,metric,from, 1,consumer);
    }

    private void restGetMetric(String project, String metric, ZonedDateTime from, int pageIndex,Consumer<List<Measure>> consumer) {
        final String fromStr =from.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'hh:mm:ss'%2B'0800"));
        final String url="/api/measures/search_history?component={project}&metrics={metrics}&from="+fromStr+"&p={p}";
        try {
            final MeasureResultIN result = sonarRest.getForObject(url, MeasureResultIN.class, project, metric,  pageIndex);
            consumer.accept(result.toMeasures(project));
            if (result.hasNext()){
                pageIndex++;
                restGetMetric(project,metric,from,pageIndex,consumer);
            }
        } catch (Exception e) {
            if (e.getMessage().matches(".*Component\\s+key.*not\\s+found.*")){
                log.error("找不到 sonarqube 项目 {}",project);
            }else{
                throw e;
            }
        }


    }
}
