package com.metrices.data.model;

public class DataMetricsProject {

    private String project;
    private String jenkinsServer;
    private String sonarServer;
    private String repo;
    private String buildJob;
    private String deployJob;
    private String sonarProjectKey;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
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

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
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
}
