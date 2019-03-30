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

    public BuildDetailsModel(boolean building, String description) {
        this.building = building;
        this.description = description;
    }

    public boolean isBuilding() {
        return building;
    }

    public String getDescription() {
        return description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getEstimatedDuration() {
        return estimatedDuration;
    }

    public String getExecutor() {
        return executor;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public String getId() {
        return id;
    }

    public boolean getKeepLog() {
        return keepLog;
    }

    public int getNumber() {
        return number;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getResult() {
        return result;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public String getUrl() {
        return url;
    }

    public GitDetails getGitDetails() {
        return gitDetails;
    }

    public CauseDetails getCauseDetails() {
        return causeDetails;
    }
}
