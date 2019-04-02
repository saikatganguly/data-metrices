package app.entity;

import java.util.Date;

//@Entity
public class SyncJob {
  //  @Id
    private String id;
    private Long timestamp;

    public SyncJob() {
    }

    public SyncJob(String id, Long timestamp) {
        this.id = id;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
