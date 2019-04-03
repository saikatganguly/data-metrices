package app.reference.pojo;

public class Repo {
    private String name;
    public String url;
    public String buildJob;
    public String deployJob;
    private String sonarProjectKey;

    public Repo(String name, String url, String buildJob, String deployJob, String sonarProjectKey) {
        this.name = name;
        this.url = url;
        this.buildJob = buildJob;
        this.deployJob = deployJob;
        this.sonarProjectKey = sonarProjectKey;
    }

    public Repo() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Repo{" +
                "url='" + url + '\'' +
                ", buildJob='" + buildJob + '\'' +
                ", deployJob='" + deployJob + '\'' +
                ", sonarProjectKey='" + sonarProjectKey + '\'' +
                '}';
    }
}
