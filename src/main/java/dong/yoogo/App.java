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
    public RestTemplate jiraRest(RestTemplateBuilder restTemplateBuilder, @Value("${provider.jira.hostAndContext}") String root) {
        return restTemplateBuilder.rootUri(root).basicAuthentication("stwk_dongyonggao", "Jira@2021").build();
    }
}
