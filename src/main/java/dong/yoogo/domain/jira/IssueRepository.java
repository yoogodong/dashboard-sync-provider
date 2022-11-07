package dong.yoogo.domain.jira;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface IssueRepository extends JpaRepository<Issue,Long> {
    @Query("select max(i.updated) from Issue i where i.projectId=:pid")
    ZonedDateTime queryLastUpdatedOf(Integer pid);
}
