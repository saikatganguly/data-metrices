package app.model;

public class BuildDetailsModel {

    private boolean building;
    private String description;
    private String displayName;
    private Long duration;
    private Long estimatedDuration;
    private String executor;
    //private String fingerprint;
    private String fullDisplayName;
    private String id;
    private boolean keepLog;
    private int number;
    private String queueId;
    private String result;
    private Long timestamp;
    private String url;
    //private String changeSets;
    //private String culprits;
    private GitDetails gitDetails;
    private CauseDetails causeDetails;

    public BuildDetailsModel(boolean building, String description, String displayName, Long duration, Long estimatedDuration, String executor, String fullDisplayName, String id, boolean keepLog, int number, String queueId, String result, Long timestamp, String url, GitDetails gitDetails, CauseDetails causeDetails) {
        this.building = building;
        this.description = description;
        this.displayName = displayName;
        this.duration = duration;
        this.estimatedDuration = estimatedDuration;
        this.executor = executor;
        this.fullDisplayName = fullDisplayName;
        this.id = id;
        this.keepLog = keepLog;
        this.number = number;
        this.queueId = queueId;
        this.result = result;
        this.timestamp = timestamp;
        this.url = url;
        this.gitDetails = gitDetails;
        this.causeDetails = causeDetails;
    }

    public BuildDetailsModel() {
    }


    public boolean isBuilding() {
        return building;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(Long estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public void setFullDisplayName(String fullDisplayName) {
        this.fullDisplayName = fullDisplayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isKeepLog() {
        return keepLog;
    }

    public void setKeepLog(boolean keepLog) {
        this.keepLog = keepLog;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public GitDetails getGitDetails() {
        return gitDetails;
    }

    public void setGitDetails(GitDetails gitDetails) {
        this.gitDetails = gitDetails;
    }

    public CauseDetails getCauseDetails() {
        return causeDetails;
    }

    public void setCauseDetails(CauseDetails causeDetails) {
        this.causeDetails = causeDetails;
    }
}
