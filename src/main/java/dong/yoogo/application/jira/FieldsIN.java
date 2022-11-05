package dong.yoogo.application.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class FieldsIN {
    @JsonProperty("issuetype")
    IssueTypeIN issueType;
    ProjectIN project;
    ZonedDateTime updated;
    ZonedDateTime created;
    StatusIN status;
}

@Data
class IssueTypeIN {
    int id;
    String name;
}

@Data
class ProjectIN {
    int id;
    String key;
    String name;
}

@Data
class StatusIN {
    int id;
    String name;
}


