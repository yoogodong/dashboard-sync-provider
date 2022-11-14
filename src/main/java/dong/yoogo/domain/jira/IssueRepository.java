package dong.yoogo.domain.jira;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {
    @Query("select max(i.updated) from Issue i where i.projectKey=:pk")
    ZonedDateTime queryLastUpdatedOf(String pk);
}
