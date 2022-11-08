package dong.yoogo.application.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class FieldsIN {
    List<fixVersion>  fixVersions;
    @JsonProperty("issuetype")
    IssueTypeIN issueType;
    ProjectIN project;
    ZonedDateTime updated;
    ZonedDateTime created;
    StatusIN status;

    Integer getFixVersionId() {
        if (fixVersions.isEmpty())
            return null;
        return fixVersions.get(0).id;
    }

    String getFixVersionName(){
        if (fixVersions.isEmpty())
            return null;
        return fixVersions.get(0).name;
    }

}

@Data
class fixVersion {
    Integer id;
    String name;
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


