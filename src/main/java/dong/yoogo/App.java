package dong.yoogo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@EnableScheduling
@SpringBootApplication
public class App {


    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public RestTemplate jiraRest(RestTemplateBuilder restTemplateBuilder,
                                 @Value("${jira.server.hostAndContext}") String root,
                                 @Value("${jira.server.username}") String username,
                                 @Value("${jira.server.password}") String password) {
        return restTemplateBuilder
                .rootUri(root)
                .basicAuthentication(username, password)
                .build();
    }


    @Bean
    public RestTemplate sonarRest(RestTemplateBuilder restTemplateBuilder,
                                 @Value("${sonarqube.server.hostAndContext}") String root,
                                 @Value("${sonarqube.server.username}") String username,
                                 @Value("${sonarqube.server.password}") String password) {
        return restTemplateBuilder
                .rootUri(root)
                .basicAuthentication(username, password)
                .build();
    }
}
