package app.reference.pojo;

public class GitDetails {
    private String repo;
    private String branch;
    private String commit;

    public GitDetails() {
    }

    public GitDetails(String repo, String branch, String commit) {
        this.repo = repo;
        this.branch = branch;
        this.commit = commit;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    @Override
    public String toString() {
        return "GitDetails{" +
                "repo='" + repo + '\'' +
                ", branch='" + branch + '\'' +
                ", commit='" + commit + '\'' +
                '}';
    }
}
