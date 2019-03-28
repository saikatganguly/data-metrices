package app.model;

public class BuildDetailsModel {

    private boolean building;
    private String description;
    private String displayName;
    private String duration;
    private String estimatedDuration;
    private String executor;
    private String fingerprint;
    private String fullDisplayName;
    private String id;
    private String keepLog;
    private String number;
    private String queueId;
    private String result;
    private String timestamp;
    private String url;
    private String changeSets;
    private String culprits;
    private GitDetails gitDetails;
    private CauseDetails causeDetails;

    public BuildDetailsModel(boolean building, String description, String displayName, String duration, String estimatedDuration, String executor, String fingerprint, String fullDisplayName, String id, String keepLog, String number, String queueId, String result, String timestamp, String url, String changeSets, String culprits, GitDetails gitDetails, CauseDetails causeDetails) {
        this.building = building;
        this.description = description;
        this.displayName = displayName;
        this.duration = duration;
        this.estimatedDuration = estimatedDuration;
        this.executor = executor;
        this.fingerprint = fingerprint;
        this.fullDisplayName = fullDisplayName;
        this.id = id;
        this.keepLog = keepLog;
        this.number = number;
        this.queueId = queueId;
        this.result = result;
        this.timestamp = timestamp;
        this.url = url;
        this.changeSets = changeSets;
        this.culprits = culprits;
        this.gitDetails = gitDetails;
        this.causeDetails = causeDetails;
    }

    public BuildDetailsModel() {
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

    public String getDuration() {
        return duration;
    }

    public String getEstimatedDuration() {
        return estimatedDuration;
    }

    public String getExecutor() {
        return executor;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public String getId() {
        return id;
    }

    public String getKeepLog() {
        return keepLog;
    }

    public String getNumber() {
        return number;
    }

    public String getQueueId() {
        return queueId;
    }

    public String getResult() {
        return result;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getUrl() {
        return url;
    }

    public String getChangeSets() {
        return changeSets;
    }

    public String getCulprits() {
        return culprits;
    }

    public GitDetails getGitDetails() {
        return gitDetails;
    }

    public CauseDetails getCauseDetails() {
        return causeDetails;
    }
}
