package app.model;

public class LastCommitTracker {
    private String repoUrl;
    private String commitId;

    public LastCommitTracker() {
    }

    public LastCommitTracker(String repoUrl, String commitId) {
        this.repoUrl = repoUrl;
        this.commitId = commitId;
    }

    public String getRepoUrl() {
        return repoUrl;
    }

    public String getCommitId() {
        return commitId;
    }
}
