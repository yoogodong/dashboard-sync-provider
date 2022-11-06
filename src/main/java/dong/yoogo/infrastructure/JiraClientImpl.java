package dong.yoogo.infrastructure;

import dong.yoogo.application.JiraClient;
import dong.yoogo.application.jira.ResultIN;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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


}
