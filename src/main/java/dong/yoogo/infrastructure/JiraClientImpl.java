package dong.yoogo.infrastructure;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import dong.yoogo.application.JiraClient;
import dong.yoogo.application.jira.ResultIN;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 */

@Slf4j
@Service
@PropertySource("classpath:config/dashboard-sync-provider.properties")
public class JiraClientImpl implements JiraClient {
    private final RestTemplate jiraRest;

    @Value("${provider.jira.pageSize}")
    private int pageSize;

    public JiraClientImpl(RestTemplate jiraRest) {
        this.jiraRest = jiraRest;
    }

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
        String url = "/latest/search?jql={jql}&fields={fields}&expand=changelog,names&startAt={startAt}&maxResults={maxResults}";
        String jql = " updated > '" + updatedFrom + "'  AND  project = '" + pid +"' order by updated asc";
        String fields = "id,components,created,fixVersions,issuetype,project,status,updated";
        final ResultIN resultIN = jiraRest.getForObject(url, ResultIN.class, jql, fields, startAt, pageSize);
        if (resultIN == null) {
            log.error("不能从 jira 获取 issue 数据");
            return;
        }
        log.debug("项目 {} 的同步进展： {}",pid, resultIN);
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
    public List<String> projectKeyList() {
        log.debug("从 jira 中查询项目列表");
        String url = "/latest/project";
        final ArrayNode forObject = jiraRest.getForObject(url, ArrayNode.class);
        final List<String> pks = new ArrayList<>();
        if (forObject == null) {
            log.error("不能从 jira 获取 issue 数据");
            return pks;
        }
        for (JsonNode jsonNode : forObject) {
            pks.add(jsonNode.get("key").textValue());
        }
        return pks;
    }

    @Override
    public ZonedDateTime jiraServerTime(){
        log.debug("查询 Jira Server 当前时间");
        String url = "/latest/serverInfo";
        final ServerInfo serverInfo = jiraRest.getForObject(url, ServerInfo.class);
        if (serverInfo==null)
            log.error("查询 jira server time 失败");
        return serverInfo==null?ZonedDateTime.now():serverInfo.getServerTime();
    }
}

@Data
class ServerInfo{
    private ZonedDateTime serverTime;
    public ZonedDateTime getServerTime() {
        return serverTime.withZoneSameInstant(ZoneId.of("UTC+8"));
    }
}

