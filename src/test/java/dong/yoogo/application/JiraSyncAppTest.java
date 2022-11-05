package dong.yoogo.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JiraSyncAppTest {
    @Autowired JiraSyncApp app;
    @Test
    void syncIssue() {
        app.syncIssue();
    }
}