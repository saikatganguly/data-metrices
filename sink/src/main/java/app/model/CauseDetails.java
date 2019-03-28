package app.model;

public class CauseDetails {
    private String causeName;
    private String userName;
    private String userId;
    private String upstream;

    public CauseDetails() {
    }

    public CauseDetails(String causeName, String userName, String userId, String upstream) {
        this.causeName = causeName;
        this.userName = userName;
        this.userId = userId;
        this.upstream = upstream;
    }

    public String getCauseName() {
        return causeName;
    }

    public void setCauseName(String causeName) {
        this.causeName = causeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpstream() {
        return upstream;
    }

    public void setUpstream(String upstream) {
        this.upstream = upstream;
    }
}
