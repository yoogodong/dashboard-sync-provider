package dong.yoogo.application.sonarqube;

import dong.yoogo.domain.sonarqube.Measure;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class MeasureResultIN {
    PagingIN paging;
    List<MeasureIN> measures;


    public boolean hasNext() {
        return paging.total > paging.pageIndex * paging.pageSize;
    }

    public List<Measure> toMeasures(String project) {
        return measures.stream().flatMap(measureIN -> measureIN.toMeasures(project)).collect(Collectors.toList());
    }
}

@Data
class PagingIN {
    Integer pageIndex;
    Integer pageSize;
    Integer total;
}

@Data
class MeasureIN {
    String metric;
    List<HistoryIN> history;


    public Stream<Measure> toMeasures(String project) {
        return history.stream().map(h -> Measure.builder()
                .metric(metric).date(h.date).value(h.value).project(project).build());
    }
}

@Data
class HistoryIN {
    ZonedDateTime date;
    String value;
}
