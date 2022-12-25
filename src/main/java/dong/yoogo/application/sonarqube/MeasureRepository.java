package dong.yoogo.application.sonarqube;

import dong.yoogo.domain.sonarqube.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

public interface MeasureRepository extends JpaRepository<Measure, Long> {

    @Query("select max(m.date) from Measure m where m.project = :project and m.metric=:metric")
    ZonedDateTime lastSynced(String project, String metric);

    List<Measure> findByProjectAndMetric(String project, String metric);

    @Query("select distinct m.project from Measure  m")
    List<String> findProjectList();

    @Transactional
    @Modifying
    void deleteByProject(String projectInDb);
}
