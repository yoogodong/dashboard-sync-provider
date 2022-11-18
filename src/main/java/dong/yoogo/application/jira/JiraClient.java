package dong.yoogo.application.jira;


import java.time.ZonedDateTime;
import java.util.List;
import java.util.function.Consumer;

public interface JiraClient {
    void queryIssuesOfProject(String pid, String updatedFrom, Consumer<ResultIN> resultINConsumer);
    List<String> projectKeyList();

    ZonedDateTime jiraServerTime();
}
