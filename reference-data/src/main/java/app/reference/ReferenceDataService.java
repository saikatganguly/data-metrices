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

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

@Service
public class ReferenceDataService {

    @Value("user-provided-data.json")
    private ClassPathResource userProvidedDataFile;

    private Organization userProvidedData;

    @PostConstruct
    public void init() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            userProvidedData = mapper.readValue(userProvidedDataFile.getFile(), Organization.class);
        } catch (IOException e) {
            System.out.println("Exception occured: " + e);
        }

        System.out.println("Organization Data: " + userProvidedData);
    }

    public List<Repo> getReposByProject(String project) {
        if (isNull(project) || project.isEmpty()) {
            return emptyList();
        }
        List<Repo> repos = new ArrayList<>();
        for (TransactionCycle transactionCycle : userProvidedData.getTransactionCycles()) {
            for (Geography geography : transactionCycle.getGeographies()) {
                for (Project proj : geography.getProjects()) {
                    if (proj.getProjectName().equalsIgnoreCase(project)) {
                        repos = proj.getRepos();
                    }
                }
            }
        }

        return repos;
    }

    public List<Project> getProjectsByGeography(String geography) {
        if (isNull(geography) || geography.isEmpty()) {
            return emptyList();
        }
        List<Project> projects = new ArrayList<>();
        for (TransactionCycle transactionCycle : userProvidedData.getTransactionCycles()) {
            for (Geography geo : transactionCycle.getGeographies()) {
                if (geo.getGeographyName().equalsIgnoreCase(geography)) {
                    projects = geo.getProjects();
                }
            }
        }
        return projects;
    }

    public List<Geography> getGeographyByTransactionCycle(String transactionCycle) {
        if (isNull(transactionCycle) || transactionCycle.isEmpty()) {
            return emptyList();
        }
        List<Geography> geographies = new ArrayList<>();
        for (TransactionCycle tc : userProvidedData.getTransactionCycles()) {
            if (tc.getTransactionCycleName().equalsIgnoreCase(transactionCycle)) {
                geographies = tc.getGeographies();
            }
        }
        return geographies;
    }

    public List<TransactionCycle> getTransactionCyclesByOrganization(String organization) {
        if (isNull(organization) || organization.isEmpty()) {
            return emptyList();
        }
        List<TransactionCycle> organizations = new ArrayList<>();
        if (userProvidedData.getOrganizationName().equalsIgnoreCase(organization)) {
            organizations = userProvidedData.getTransactionCycles();
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
        om.writeValue(new File("userProvidedDataFile.json"),org);

        String s = om.writeValueAsString(org);
        Organization organization = om.readValue(s, Organization.class);
        System.out.println(organization);
    }*/
}
