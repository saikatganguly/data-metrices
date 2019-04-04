package app.reference.pojo;

import java.util.List;

public class Geography {
    private String id;
    public String geographyName;
    public List<Project> projects;

    public Geography() {
    }

    public Geography(String id, String geographyName, List<Project> projects) {
        this.id = id;
        this.geographyName = geographyName;
        this.projects = projects;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGeographyName() {
        return geographyName;
    }

    public void setGeographyName(String geographyName) {
        this.geographyName = geographyName;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", geographyName='" + geographyName + '\'' +
                ", projects=" + projects +
                '}';
    }
}
