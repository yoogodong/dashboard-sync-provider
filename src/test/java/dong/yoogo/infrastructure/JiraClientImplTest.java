package dong.yoogo.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@SpringBootTest
class JiraClientImplTest {

    @Autowired
    RestTemplate jiraRest;

    @Test
    void name() {
        final Map object = jiraRest.getForObject("https://jira.atlassian.com/rest/api/latest/issue/JRA-9?fields=issuetype", Map.class);
        System.out.println(object);
    }
}