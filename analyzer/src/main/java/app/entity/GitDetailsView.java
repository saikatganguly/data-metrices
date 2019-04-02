package app.entity;

import java.util.Date;

//@Entity
public class GitDetailsView {
  //  @Id
    private String id;
    private String repo;
    private String branch;
    private String commit;
    private Date date;

    public GitDetailsView() {
    }

    public GitDetailsView(String id, String repo, String branch, String commit, Date date) {
        this.id = id;
        this.repo = repo;
        this.branch = branch;
        this.commit = commit;
        this.date = date;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
