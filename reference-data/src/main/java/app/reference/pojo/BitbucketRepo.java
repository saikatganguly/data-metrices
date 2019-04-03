package app.reference.pojo;

import java.net.MalformedURLException;
import java.util.Objects;

public class BitbucketRepo {

    public static final String BITBUCKET_REST_API_VERSION = "/rest/api/1.0/";

    private String hostName;
    private String projectName;
    private String repoName;

    public BitbucketRepo(String hostName, String projectName) {
        this.hostName = hostName;
        this.projectName = projectName;
    }

    public BitbucketRepo(String hostName, String projectName, String repoName) {
        this.hostName = hostName;
        this.projectName = projectName;
        this.repoName = repoName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoUrl() throws MalformedURLException {
        return hostName + BITBUCKET_REST_API_VERSION + "projects/" + projectName + "/repos/" + repoName;
    }

    public String getAllRepoUrl() {
        return hostName + BITBUCKET_REST_API_VERSION + projectName + "/repos";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BitbucketRepo that = (BitbucketRepo) o;
        return Objects.equals(hostName, that.hostName) &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(repoName, that.repoName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostName, projectName, repoName);
    }

    @Override
    public String toString() {
        return "BitbucketRepo{" +
                "hostName='" + hostName + '\'' +
                ", projectName='" + projectName + '\'' +
                ", repoName='" + repoName + '\'' +
                '}';
    }
}
