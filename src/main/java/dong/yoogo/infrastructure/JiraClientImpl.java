package dong.yoogo.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import dong.yoogo.application.JiraClient;
import dong.yoogo.application.jira.ResultIN;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 */
@AllArgsConstructor
@Slf4j
@Service
public class JiraClientImpl implements JiraClient {
    private RestTemplate jiraRest;
    private final String url = "https://jira.atlassian.com/rest/api/latest/search?jql={jql}&fields={fields}&expand=changelog,names&startAt={startAt}&maxResults={maxResults}";
    private final String fields = "id,project,issuetype,status,updated,created";
    private final int pageSize = 10;
    private final String projectUrl = "https://jira.atlassian.com/rest/api/latest/project";


    /**
     * query issues  from jira
     *
     * @param pid           project id
     * @param updatedFrom   updated start time
     * @param resultHandler result handler
     */
    @Override
    public void queryIssuesOfProject(String pid, String updatedFrom, Consumer<ResultIN> resultHandler) {
        pagingQuery(pid, updatedFrom, 0, resultHandler);
    }

    private void pagingQuery(String pid, String updatedFrom, int startAt, Consumer<ResultIN> resultINConsumer) {
        String jql = "updated>='" + updatedFrom + "'  AND  project = " + pid;
        final ResultIN resultIN = jiraRest.getForObject(url, ResultIN.class, jql, fields, startAt, pageSize);
        if (resultIN == null) {
            log.error("不能从 jira 获取 issue 数据");
            return;
        }
        log.debug("query jira result = {}", resultIN);
        resultINConsumer.accept(resultIN);
        if (resultIN.hasNext()) {
            pagingQuery(pid, updatedFrom, startAt + pageSize, resultINConsumer);
        }
    }

    /**
     * query jira for id list of all project
     *
     * @return id list of all project from jira
     */
    @Override
    public List<String> getAllProjectsId() {
        final ArrayNode forObject = jiraRest.getForObject(projectUrl, ArrayNode.class);
        final List<String> ids = new ArrayList<>();
        if (forObject == null) {
            log.error("不能从 jira 获取 issue 数据");
            return ids;
        }
        for (JsonNode jsonNode : forObject) {
            ids.add(jsonNode.get("id").textValue());
        }
        return ids;
    }


}
