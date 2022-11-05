package dong.yoogo.domain.jira;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

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
    private String issueKey;
    private Integer issueTypeId;
    private String issueTypeName;
    private Integer projectId;
    private String projectKey;
    private ZonedDateTime updated;
    private ZonedDateTime created;
    private Integer statusId;
    private String  statusName;
}
