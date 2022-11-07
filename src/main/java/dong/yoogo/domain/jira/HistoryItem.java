package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class HistoryItem {
    private String field;
    private String fieldtype;
    private String fromValue;
    private String fromString;
    private String toValue;
    private String toString;
}
