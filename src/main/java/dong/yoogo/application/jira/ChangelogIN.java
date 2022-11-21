package dong.yoogo.application.jira;

import dong.yoogo.domain.jira.History;
import dong.yoogo.domain.jira.HistoryItem;
import dong.yoogo.domain.jira.IssueRepository;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChangelogIN {
    int startAt;
    int maxResults;
    int total;
    List<HistoryIN> histories;

    public List<History> toHistoryList(IssueRepository repository) {
       return  histories.stream().map(HistoryIN::toHistory).collect(Collectors.toList());
    }
}
@Data
class HistoryIN {
    Integer id;
    AuthorIN author=new AuthorIN();//jira 中此字段有时为null
    ZonedDateTime created;
    List<HistoryItemIN> items;

    public History toHistory() {
        return History.builder()
                .id(id)
                .authorDisplayName(author.displayName)
                .authorKey(author.key)
                .created(created)
                .items(items.stream().map(HistoryItemIN::toItem).collect(Collectors.toList()))
                .build();
    }
}
@Data
class AuthorIN {
    String name;
    String key;
    String displayName;
    boolean active;
}

@Data
class HistoryItemIN {
    String field;
    String fieldtype;
    String from;
    String fromString;
    String to;
    String toString;

    public HistoryItem toItem() {
        return HistoryItem.builder()
                .field(field)
                .fieldType(fieldtype)
                .fromValue(limitLength(from))
                .toValue(limitLength(to))
                .fromString(limitLength(fromString))
                .toString(limitLength(toString))
                .build();
    }

    private String limitLength(String value){
        final int limit = 20;
        if (value==null || value.length()<= limit)
            return value;
        return value.substring(0, limit);
    }
}
