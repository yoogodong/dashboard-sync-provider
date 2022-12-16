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
import java.time.LocalDate;
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
    private String summary;
    private ZonedDateTime updated;
    @JoinColumn(name = "issue_id", nullable = false, updatable = false)
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<History> histories;

    private String labels;
    private LocalDate reqStart0;
    private LocalDate reqEnd0;
    private LocalDate devStart0;
    private LocalDate devEnd0;
    private LocalDate sitStart0;
    private LocalDate sitEnd0;
    private LocalDate innerTestStart0;
    private LocalDate innerTestEnd0;
    private LocalDate uatStart0;
    private LocalDate uatEnd0;
    private LocalDate pub0;
    private LocalDate reqStart1;
    private LocalDate reqEnd1;
    private LocalDate devStart1;
    private LocalDate devEnd1;
    private LocalDate sitStart1;
    private LocalDate sitEnd1;
    private LocalDate innerTestStart1;
    private LocalDate innerTestEnd1;
    private LocalDate uatStart1;
    private LocalDate uatEnd1;
    private LocalDate pub1;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
