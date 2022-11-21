package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.List;

/**
 *
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Issue implements Persistable<Integer> {
    @Id
    private Integer id;
    private int changelogSize;
    private Integer componentId;
    private String componentName;
    private ZonedDateTime created;
    private Integer fixVersionId;
    private String fixVersionName;
    @Column(unique = true, length = 20)
    private String issueKey;
    private Integer issueTypeId;
    @Column(length = 20)
    private String issueTypeName;
    private Integer parentId;
    private Integer projectId;
    @Column(length = 20)
    private String projectKey;
    @Column(length = 50)
    private String projectName;
    private Integer statusId;
    @Column(length = 50)
    private String statusName;
    private ZonedDateTime updated;
    @JoinColumn(name = "issue_id", nullable = false, updatable = false)
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<History> histories;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
