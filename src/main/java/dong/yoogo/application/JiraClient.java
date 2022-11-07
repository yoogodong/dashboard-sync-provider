package dong.yoogo.application;


import dong.yoogo.application.jira.ResultIN;

import java.util.List;
import java.util.function.Consumer;

public interface JiraClient {
    void pagedSync(Consumer<ResultIN> resultINConsumer);

    void pagedSync(String pid, Consumer<ResultIN> resultINConsumer);

    List<String> getAllProjectsId();
}
