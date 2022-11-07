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
import java.util.Iterator;
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
    private final String jql = "updated>-1301d AND updated <-1290d  AND  project IN (12200,14510)";
    private final String fields = "id,project,issuetype,status,updated,created";
    private final int pageSize = 10;

    private final String projectUrl = "https://jira.atlassian.com/rest/api/latest/project";

    @Override
    public void pagedSync(Consumer<ResultIN> resultINConsumer) {
        pagedSync(0, resultINConsumer);
    }

    private void pagedSync(int startAt, Consumer<ResultIN> resultINConsumer) {
        final ResultIN resultIN = jiraRest.getForObject(url, ResultIN.class, jql, fields, startAt, pageSize);
        log.debug("query jira result = {}", resultIN);
        resultINConsumer.accept(resultIN);
        if (resultIN.hasNext()) {
            pagedSync(startAt+pageSize, resultINConsumer);
        }
    }

    /**
     * 从指定的更新起始时间，获取指定的项目（pid) 的所有 issue
     * @param pid  project id
     * @param resultINConsumer callback
     */
    @Override
    public void pagedSync(String pid,Consumer<ResultIN> resultINConsumer) {
        pagedSync(pid,0,resultINConsumer);
    }
    private void pagedSync(String pid, int startAt, Consumer<ResultIN> resultINConsumer) {
        String jql_project = "updated>-1301d AND updated <-1290d  AND  project = "+pid;
        final ResultIN resultIN = jiraRest.getForObject(url, ResultIN.class, jql_project, fields, startAt, pageSize);
        log.debug("query jira result = {}", resultIN);
        resultINConsumer.accept(resultIN);
        if (resultIN.hasNext()) {
            pagedSync(pid,startAt+pageSize, resultINConsumer);
        }
    }

    /**
     * 从 jira 中获取所有的项目的 id
     * @return id list of all project in jira
     */
    @Override
    public List<String> getAllProjectsId() {
        final ArrayNode forObject = jiraRest.getForObject(projectUrl, ArrayNode.class);
        final Iterator<JsonNode> projects = forObject.iterator();
        final List<String> ids = new ArrayList<>();
        while (projects.hasNext()){
            ids.add(projects.next().get("id").textValue());
        }
        return ids;
    }




}
