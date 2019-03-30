package app.model;

import java.net.MalformedURLException;

public class BitbucketRepo {

    public static final String BITBUCKET_REST_API_VERSION = "/rest/api/1.0/";

    private String hostName;
    private String projectName;
    private String repoName;

    public BitbucketRepo(String hostName) {
        this.hostName = hostName;
    }

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

        if (hostName != null ? !hostName.equals(that.hostName) : that.hostName != null) return false;
        if (projectName != null ? !projectName.equals(that.projectName) : that.projectName != null) return false;
        return repoName != null ? repoName.equals(that.repoName) : that.repoName == null;
    }

    @Override
    public int hashCode() {
        int result = hostName != null ? hostName.hashCode() : 0;
        result = 31 * result + (projectName != null ? projectName.hashCode() : 0);
        result = 31 * result + (repoName != null ? repoName.hashCode() : 0);
        return result;
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
