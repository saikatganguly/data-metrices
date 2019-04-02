package app.reference.pojo;

public class Repo {
    public String URL;
    public String buildJob;
    public String deployJob;
    private String sonarProjectKey;

    public Repo(String URL, String buildJob, String deployJob, String sonarProjectKey) {
        this.URL = URL;
        this.buildJob = buildJob;
        this.deployJob = deployJob;
        this.sonarProjectKey = sonarProjectKey;
    }

    public Repo() {
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getBuildJob() {
        return buildJob;
    }

    public void setBuildJob(String buildJob) {
        this.buildJob = buildJob;
    }

    public String getDeployJob() {
        return deployJob;
    }

    public void setDeployJob(String deployJob) {
        this.deployJob = deployJob;
    }

    public String getSonarProjectKey() {
        return sonarProjectKey;
    }

    public void setSonarProjectKey(String sonarProjectKey) {
        this.sonarProjectKey = sonarProjectKey;
    }

    @Override
    public String toString() {
        return "Repo{" +
                "URL='" + URL + '\'' +
                ", buildJob='" + buildJob + '\'' +
                ", deployJob='" + deployJob + '\'' +
                ", sonarProjectKey='" + sonarProjectKey + '\'' +
                '}';
    }
}
