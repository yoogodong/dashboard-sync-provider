package dong.yoogo.application;

import dong.yoogo.application.sonarqube.FormattedMeasureRepository;
import dong.yoogo.application.sonarqube.MeasureRepository;
import dong.yoogo.application.sonarqube.SonarQubeClient;
import dong.yoogo.domain.sonarqube.FormattedMeasure;
import dong.yoogo.domain.sonarqube.Measure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class SonarQubeSyncApp {
    private final SonarQubeClient sonar;
    private final MeasureRepository repository;
    private final FormattedMeasureRepository formattedMeasureRepository;
    @Value("${sonarqube.sync.delay.ms}")
    private int delay;

    public SonarQubeSyncApp(SonarQubeClient sonar, MeasureRepository repository,
                            FormattedMeasureRepository formattedMeasureRepository) {
        this.sonar = sonar;
        this.repository = repository;
        this.formattedMeasureRepository=formattedMeasureRepository;
    }

    @Scheduled(fixedDelayString = "${sonarqube.sync.delay.ms}")
    public void sync() {
        final Instant start = Instant.now();
        final List<String> projects = sonar.projectsNeedToSync();
        deleteUnwantedProjectNotIn(projects);
        final List<String> metrics = sonar.metricsNeedToSync();
        for (String pro : projects) {
            log.info("start sync project {}", pro);
            metrics.stream().parallel().forEach(m -> syncAndSave(pro, m, from(pro, m)));
        }
        final Duration duration = Duration.between(start, Instant.now());
        log.info("已完成所有 SonarQube 项目同步,耗时{}, 下次同步将在 {} 分钟之后", duration, delay / 60000);
    }

    private ZonedDateTime from(String project, String metric) {
        ZonedDateTime from = repository.lastSynced(project, metric);
        log.debug("项目 {} 最后同步时间 {}", project, from);
        if (from == null) {
            return ZonedDateTime.now().minusYears(2);
        } else {
            return from.plusSeconds(1L);
        }
    }

    private void syncAndSave(String project, String metric, ZonedDateTime from) {
        log.debug("sync sonar project {} for metric {} from {}", project, metric, from);
        sonar.restGetMetric(project, metric, from, repository::saveAll);
    }

    private void deleteUnwantedProjectNotIn(List<String> projects) {
        List<String> projectsInDb = repository.findProjectList();
        projectsInDb.forEach(inDb->{
            if (!projects.contains(inDb)){
                repository.deleteByProject(inDb);
            }
        });
    }

    /**
     * 处理 Measure 成为 FormattedMeasure， 按天分布度量值
     */
    @Scheduled(cron = "0 0 0,6,12,18 * * ?")
    public void formatMeasure() {
        log.info("将 measure 格式化成 formatted measure,以使度量值值按连续的日期分布");
        final Instant start = Instant.now();
        final List<String> projects = sonar.projectsNeedToSync();
        final List<String> metrics = Arrays.asList("blocker_violations", "critical_violations");
        formattedMeasureRepository.deleteAllInBatch();
        projects.forEach(pro -> metrics.forEach(metric -> {
            List<Measure> measures = repository.findByProjectAndMetric(pro, metric);
            Map<LocalDate, String> dateAndValue = new HashMap<>();
            measures.forEach(measure -> dateAndValue.put(measure.getDate().toLocalDate(), measure.getValue()));
            List<FormattedMeasure> formattedMeasures = new ArrayList<>();
            dateAndValue.keySet().stream().sorted(Comparator.naturalOrder()).forEach(currentDate -> {
                if (formattedMeasures.isEmpty()) {
                    formattedMeasures.add(buildFormattedMeasure(pro, metric, currentDate, dateAndValue.get(currentDate)));
                }else{
                    while(true) {
                        final FormattedMeasure lastFormatted = formattedMeasures.get(formattedMeasures.size() - 1);
                        if (lastFormatted.getDate().plusDays(1).equals(currentDate)){
                            formattedMeasures.add(buildFormattedMeasure(pro,metric,currentDate,dateAndValue.get(currentDate)));
                            break;
                        }else {
                            final FormattedMeasure newLast = lastFormatted.withDate(lastFormatted.getDate().plusDays(1));
                            formattedMeasures.add(newLast);
                        }
                    }
                }
            });
            for (FormattedMeasure last = formattedMeasures.get(formattedMeasures.size() - 1);
                 last.getDate().isBefore(LocalDate.now()); ) {
                formattedMeasures.add(last.withDate(last.getDate().plusDays(1)));
                last = formattedMeasures.get(formattedMeasures.size() - 1);
            }
            formattedMeasureRepository.saveAll(formattedMeasures);
        }));
        final Duration duration = Duration.between(start, Instant.now());
        log.info("格式化过程共耗时{}, 下次同步的时间由 cron 表达式 {} 决定", duration, "0 0 0,6,12,18 * * ?");
    }

    private FormattedMeasure buildFormattedMeasure(String pro, String metric, LocalDate localDate, String value) {
        return FormattedMeasure.builder()
                .date(localDate)
                .value(value)
                .metric(metric)
                .project(pro).build();
    }
}
