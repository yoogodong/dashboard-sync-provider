package dong.yoogo.application.sonarqube;

import dong.yoogo.domain.sonarqube.Measure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;

public interface MeasureRepository extends JpaRepository<Measure, Long> {

    @Query("select max(m.date) from Measure m where m.project = :project and m.metric=:metric")
    ZonedDateTime lastSynced(String project, String metric);

}
