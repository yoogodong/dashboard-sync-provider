package dong.yoogo.application;


import dong.yoogo.application.jira.ResultIN;

import java.util.List;
import java.util.function.Consumer;

public interface JiraClient {
    void queryIssuesOfProject(String pid, String updatedFrom, Consumer<ResultIN> resultINConsumer);
    List<String> getAllProjectsId();
}
