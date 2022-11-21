package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class HistoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String field;
    @Column(length = 20)
    private String fieldType;
    @Column(length = 20)
    private String fromValue;
    @Column(length = 20)
    private String fromString;
    @Column(length = 20)
    private String toValue;
    @Column(length = 20)
    private String toString;
}
