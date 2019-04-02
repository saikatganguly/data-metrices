package app.entity;

import java.util.Date;

//@Entity
public class GitCommitsView {
  //  @Id
    private String id;
    private String repo;
    private String branch;
    private long count;
    private Date date;

    public GitCommitsView() {
    }

    public GitCommitsView(String id, String repo, String branch, long count, Date date) {
        this.id = id;
        this.repo = repo;
        this.branch = branch;
        this.count = count;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void incrementCount() {
        count++;
    }
}
