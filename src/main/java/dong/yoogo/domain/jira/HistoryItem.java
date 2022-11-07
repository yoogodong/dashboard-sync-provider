package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class HistoryItem {
    @Column(length = 20)
    private String field;
    @Column(length = 20)
    private String fieldtype;
    @Column(length = 20)
    private String fromValue;
    @Column(length = 20)
    private String fromString;
    @Column(length = 20)
    private String toValue;
    @Column(length = 20)
    private String toString;
}
