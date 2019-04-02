package app.reference;

import app.reference.pojo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

@Service
public class ReferenceData {

    @Value("reference-data.json")
    private ClassPathResource file;

    private Organization data;

    @PostConstruct
    public void init() {
        ObjectMapper om = new ObjectMapper();
        try {
            data = om.readValue(file.getFile(), Organization.class);
        } catch (IOException e) {
            System.out.println("Exception occured: " + e);
        }

        System.out.println("Organization Data: "+data);
    }

    public List<String> getReposByProject(String project) {
        if (isNull(project) || project.isEmpty()) {
            return emptyList();
        }
        List<String> repos = new ArrayList<>();
        for (TransactionCycle transactionCycle : data.getTransactionCycles()) {
            for (Geography geography : transactionCycle.getGeographies()) {
                for (Project proj : geography.getProjects()) {
                    if (proj.getProjectName().equalsIgnoreCase(project)) {
                        for (Repo repo : proj.getRepos()) {
                            repos.add(repo.getUrl());
                        }
                    }
                }
            }
        }

        return repos;
    }

    public List<String> getProjectsByGeography(String geography) {
        if (isNull(geography) || geography.isEmpty()) {
            return emptyList();
        }
        List<String> projects = new ArrayList<>();
        for (TransactionCycle transactionCycle : data.getTransactionCycles()) {
            for (Geography geo : transactionCycle.getGeographies()) {
                if (geo.getGeographyName().equalsIgnoreCase(geography)) {
                    for (Project project : geo.getProjects()) {
                        projects.add(project.getProjectName());
                    }
                }
            }
        }
        return projects;
    }

    public List<String> getGeographyByTransactionCycle(String transactionCycle) {
        if (isNull(transactionCycle) || transactionCycle.isEmpty()) {
            return emptyList();
        }
        List<String> geographies = new ArrayList<>();
        for (TransactionCycle tc : data.getTransactionCycles()) {
            if (tc.getTransactionCycleName().equalsIgnoreCase(transactionCycle)) {
                for (Geography geography : tc.getGeographies()) {
                    geographies.add(geography.getGeographyName());
                }
            }
        }
        return geographies;
    }

    public List<String> getTransactionCyclesByOrganization(String organization) {
        if (isNull(organization) || organization.isEmpty()) {
            return emptyList();
        }
        List<String> organizations = new ArrayList<>();
        if (data.getOrganizationName().equalsIgnoreCase(organization)) {
            organizations.add(data.getOrganizationName());
        }
        return organizations;
    }


    /*public static void main(String[] args) throws IOException {
        Repo repo = new Repo("some-reference-repo","some-refereence-build-job", "some-reference-deploy-job","some-reference-sonar-key");
        Project project = new Project("some-reference-project", asList(repo),"some-reference-jenkins-server","some-reference-sonar-server");
        Geography geography = new Geography("some-reference-geography", asList(project));
        TransactionCycle tc = new TransactionCycle("some-reference-tc", asList(geography));
        Organization org = new Organization("Barclays", asList(tc));

        ObjectMapper om = new ObjectMapper();
        om.writeValue(new File("file.json"),org);

        String s = om.writeValueAsString(org);
        Organization organization = om.readValue(s, Organization.class);
        System.out.println(organization);
    }*/
}
