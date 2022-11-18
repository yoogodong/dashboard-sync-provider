package dong.yoogo.application.sonarqube;

import dong.yoogo.domain.sonarqube.Measure;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface SonarQubeClient {
    List<String> projectsNeedToSync();

    List<String> metricsNeedToSync();

    void restGetMetric(String project, String metric, ZonedDateTime from, Consumer<List<Measure>> consumer);
}
