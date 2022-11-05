package dong.yoogo.application;


import dong.yoogo.application.jira.ResultIN;

public interface JiraClient {
    ResultIN getIssues(int startAt, int maxResults);
}
