package dong.yoogo.application.sonarqube;

import dong.yoogo.domain.sonarqube.FormattedMeasure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FormattedMeasureRepository extends JpaRepository<FormattedMeasure,Long> {
}
