package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

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
public class Issue {
    @Id
    private Long id;
    private ZonedDateTime created;
    @Column(unique = true,length = 20)
    private String issueKey;
    private Integer issueTypeId;
    private String issueTypeName;
    private Integer projectId;
    private String projectKey;
    private String projectName;
    private Integer statusId;
    private String  statusName;
    private ZonedDateTime updated;
    @JoinColumn(name = "issue_id")
    @OneToMany(cascade = CascadeType.ALL)
    private List<History> histories;
}
