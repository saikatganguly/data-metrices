package app.reference.pojo;

import java.util.List;

public class Project {
    private String id;
    private String projectName;
    public List<Repo> repos;
    public String jenkinsServer;
    public String sonarServer;

    public Project() {
    }

    public Project(String id, String projectName, List<Repo> repos, String jenkinsServer, String sonarServer) {
        this.id = id;
        this.projectName = projectName;
        this.repos = repos;
        this.jenkinsServer = jenkinsServer;
        this.sonarServer = sonarServer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Repo> getRepos() {
        return repos;
    }

    public void setRepos(List<Repo> repos) {
        this.repos = repos;
    }

    public String getJenkinsServer() {
        return jenkinsServer;
    }

    public void setJenkinsServer(String jenkinsServer) {
        this.jenkinsServer = jenkinsServer;
    }

    public String getSonarServer() {
        return sonarServer;
    }

    public void setSonarServer(String sonarServer) {
        this.sonarServer = sonarServer;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", projectName='" + projectName + '\'' +
                ", repos=" + repos +
                ", jenkinsServer='" + jenkinsServer + '\'' +
                ", sonarServer='" + sonarServer + '\'' +
                '}';
    }
}
