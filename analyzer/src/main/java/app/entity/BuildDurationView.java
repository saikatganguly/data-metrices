package app.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class BuildDurationView {
    @Id
    private String id;
    private String repo;
    private String branch;
    private long duration;
    private Date date;

    public BuildDurationView() {
    }

    public BuildDurationView(String id, String repo, String branch, long duration, Date date) {
        this.id = id;
        this.repo = repo;
        this.branch = branch;
        this.duration = duration;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
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
