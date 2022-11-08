package dong.yoogo.application.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;

@Data
public class FieldsIN {
    ZonedDateTime created;
    List<IdKeyName> components;
    List<IdKeyName>  fixVersions;
    @JsonProperty("issuetype")
    IdKeyName issueType;
    IdKeyName project;
    ZonedDateTime updated;
    IdKeyName status;

    void postConstruct(){
       components = components.isEmpty()? Collections.singletonList(new IdKeyName()):components;
       fixVersions = fixVersions.isEmpty()? Collections.singletonList(new IdKeyName()):fixVersions;
    }

    Integer getComponentId(){
        return components.get(0).id;
    }

    String getComponentName(){
        return components.get(0).name;
    }

    Integer getFixVersionId() {
        return fixVersions.get(0).id;
    }

    String getFixVersionName(){
        return fixVersions.get(0).name;
    }

}
@Data
class IdKeyName {
    Integer id;
    String key;
    String name;
}




