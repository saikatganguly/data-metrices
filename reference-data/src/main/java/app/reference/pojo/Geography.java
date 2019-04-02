package app.reference.pojo;

import java.util.List;

public class Geography {
    public String geographyName;
    public List<Project> projects;

    public Geography() {
    }

    public Geography(String geographyName, List<Project> projects) {
        this.geographyName = geographyName;
        this.projects = projects;
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
        return "Geography{" +
                "geographyName='" + geographyName + '\'' +
                ", projects=" + projects +
                '}';
    }
}
