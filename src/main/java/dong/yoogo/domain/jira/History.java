package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.domain.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class History implements Persistable<Integer> {
    @Id
    private Integer id;
    @Column(length = 50)
    private String authorKey;
    @Column(length = 100)
    private String authorDisplayName;
    private ZonedDateTime created;
    @OneToMany
    @JoinColumn(name="history_id",nullable = false, updatable = false)
    @Cascade({CascadeType.PERSIST,CascadeType.MERGE})
    private List<HistoryItem> items;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
