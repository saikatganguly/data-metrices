package app.model;

import java.util.Objects;

public class CommitInfo {

    private String id;
    private String authorName;
    private String authorEmailAddress;
    private String message;
    private String tag;
    private long timeStamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorEmailAddress() {
        return authorEmailAddress;
    }

    public void setAuthorEmailAddress(String authorEmailAddress) {
        this.authorEmailAddress = authorEmailAddress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommitInfo that = (CommitInfo) o;
        return timeStamp == that.timeStamp &&
                Objects.equals(id, that.id) &&
                Objects.equals(authorName, that.authorName) &&
                Objects.equals(authorEmailAddress, that.authorEmailAddress) &&
                Objects.equals(message, that.message) &&
                Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorName, authorEmailAddress, message, tag, timeStamp);
    }

    @Override
    public String toString() {
        return "CommitInfo{" +
                "id='" + id + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorEmailAddress='" + authorEmailAddress + '\'' +
                ", tag='" + tag + '\'' +
                ", timeStamp=" + timeStamp +
                ", message='" + message + '\'' +
                '}';
    }
}
