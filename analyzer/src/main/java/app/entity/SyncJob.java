package app.entity;

//@Entity
public class SyncJob {
  //  @Id
    private String id;
    private String jobId;

    public SyncJob() {
    }

    public SyncJob(String id, String jobId) {
        this.id = id;
        this.jobId = jobId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
}
