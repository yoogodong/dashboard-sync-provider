package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class History {
    @Id
    private Long id;
    private String authorKey;
    private String authorDisplayName;
    private ZonedDateTime created;
    @ElementCollection
    private List<HistoryItem> items;
}
