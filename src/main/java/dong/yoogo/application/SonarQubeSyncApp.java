package dong.yoogo.application;

import dong.yoogo.application.sonarqube.MeasureRepository;
import dong.yoogo.application.sonarqube.SonarQubeClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@Slf4j
@EnableAsync
public class SonarQubeSyncApp {
    private final SonarQubeClient sonar;
    private final MeasureRepository repository;
    @Value("${sonarqube.sync.delay.ms}")
    private int delay;

    public SonarQubeSyncApp(SonarQubeClient sonar, MeasureRepository repository) {
        this.sonar = sonar;
        this.repository = repository;
    }

    @Async
    @Scheduled(fixedDelayString = "${sonarqube.sync.delay.ms}")
    public void sync() {
        final Instant start = Instant.now();
        final List<String> projects = sonar.projectsNeedToSync();
        final List<String> metrics = sonar.metricsNeedToSync();
        for (String pro : projects) {
            log.info("start sync project {}", pro);
            metrics.stream().parallel().forEach(m -> syncAndSave(pro, m, from(pro, m)));
        }
        final Duration duration = Duration.between(start, Instant.now());
        log.info("已完成所有 SonarQube 项目同步,耗时{}, 下次同步将在 {} 分钟之后", duration,delay/60000);
    }

    public ZonedDateTime from(String project, String metric) {
        ZonedDateTime from = repository.lastSynced(project, metric);
        log.debug("项目 {} 最后同步时间 {}",project,from);
        if (from == null) {
            return ZonedDateTime.now().minusYears(2);
        } else {
            return from.plusSeconds(1L);
        }
    }

    private void syncAndSave(String project, String metric, ZonedDateTime from) {
        log.debug("sync sonar project {} for metric {} from {}", project,metric,from);
        sonar.restGetMetric(project, metric, from, repository::saveAll);
    }
}
