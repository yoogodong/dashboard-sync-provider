package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class History implements Persistable<Long> {
    @Id
    private Long id;
    @Column(length = 50)
    private String authorKey;
    @Column(length = 100)
    private String authorDisplayName;
    private ZonedDateTime created;
    @ElementCollection
    private List<HistoryItem> items;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
