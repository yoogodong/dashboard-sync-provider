package dong.yoogo.application.jira;

import com.fasterxml.jackson.annotation.JsonProperty;
import dong.yoogo.domain.jira.Issue;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class FieldsIN {
    ZonedDateTime created;
    List<IdKeyName> components;
    List<IdKeyName> fixVersions;
    @JsonProperty("issuetype")
    IdKeyName issueType;
    IdKeyName parent;
    IdKeyName project;
    ZonedDateTime updated;
    IdKeyName status;
    String summary;
    List<String> labels;
    @JsonProperty("customfield_14000")
    LocalDate devStart0;
    @JsonProperty("customfield_10700")
    LocalDate devStart1;
    @JsonProperty("customfield_10101")
    LocalDate devEnd0;
    @JsonProperty("customfield_10104")
    LocalDate devEnd1;
    @JsonProperty("customfield_12401")
    LocalDate sitStart0;
    @JsonProperty("customfield_12400")
    LocalDate sitStart1;
    @JsonProperty("customfield_10418")
    LocalDate sitEnd0;
    @JsonProperty("customfield_12001")
    ZonedDateTime sitEnd1;
    @JsonProperty("customfield_14001")
    LocalDate innerTestStart0;
    @JsonProperty("customfield_10502")
    LocalDate innerTestStart1;
    @JsonProperty("customfield_10407")
    LocalDate innerTestEnd0;
    @JsonProperty("customfield_10106")
    LocalDate innerTestEnd1;
    @JsonProperty("customfield_14002")
    LocalDate uatStart0;
    @JsonProperty("customfield_14713")
    LocalDate uatStart1;
    @JsonProperty("customfield_10103")
    LocalDate uatEnd0;
    @JsonProperty("customfield_10105")
    LocalDate uatEnd1;
    @JsonProperty("customfield_10217")
    LocalDate pub0;
    @JsonProperty("customfield_10219")
    LocalDate pub1;


    public Issue.IssueBuilder toIssue(Issue.IssueBuilder issueBuilder) {
        final Issue.IssueBuilder builder = issueBuilder
                .componentId(components.isEmpty()?null:components.get(0).id)
                .componentName(components.isEmpty()?null:components.get(0).name)
                .created(created)
                .fixVersionId(fixVersions.isEmpty()?null:fixVersions.get(0).id)
                .fixVersionName(fixVersions.isEmpty()?null:fixVersions.get(0).name)
                .issueTypeId(issueType.id)
                .issueTypeName(issueType.name)
                .parentId(parent == null ? null : parent.id)
                .projectId(project.id)
                .projectKey(project.key)
                .projectName(project.name)
                .statusId(status.id)
                .statusName(status.name)
                .summary(head(summary, 100))
                .updated(updated)
                .labels(labels==null||labels.size()==0?null:labels.stream().reduce((a, b) -> a.concat(",").concat(b)).orElse("错误"));
        return customizedFields(builder);
    }

    private Issue.IssueBuilder customizedFields(Issue.IssueBuilder builder) {
        return builder.devStart0(devStart0).devStart1(devStart1).devEnd0(devEnd0).devEnd1(devEnd1)
                .sitStart0(sitStart0).sitStart1(sitStart1).sitEnd0(sitEnd0).sitEnd1(sitEnd1==null?null:sitEnd1.toLocalDate())
                .innerTestStart0(innerTestStart0).innerTestStart1(innerTestStart1).innerTestEnd0(innerTestEnd0).innerTestEnd1(innerTestEnd1)
                .uatStart0(uatStart0).uatStart1(uatStart1).uatEnd0(uatEnd0).uatEnd1(uatEnd1)
                .pub0(pub0).pub1(pub1);
    }

    private String head(String str, int max) {
        if (str == null) return null;
        return str.length() > max ? str.substring(0, max) : str;
    }

}

@Data
class IdKeyName {
    Integer id;
    String key;
    String name;
}




