package app.reference;

import app.reference.pojo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

@Service
public class ReferenceData {

    public static final List<String> REPOS1 = asList("sampleRepo");
    public static final List<String> REPOS2 = asList("sampleRepo");
    public static final List<String> PROJECTS1 = asList("CardNG");
    public static final List<String> PROJECTS2 = asList("CL");
    public static final List<String> GEOGRAPHY1 = asList("CardNG");
    public static final List<String> GEOGRAPHY2 = asList("CL");
    public static final List<String> TRANSACTION_CYCLE1 = asList("Card");
    public static final List<String> TRANSACTION_CYCLE2 = asList("Payment");

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
    }

    public List<String> getReposByProject(String project) {
        return REPOS1;
    }

    public List<String> getProjectsByGeography(String geography) {
        Map<String, List<String>> response = new HashMap<>();
        response.put("US", PROJECTS1);
        response.put("UK", PROJECTS2);
        return PROJECTS1;
    }

    public List<String> getGeographyByTransactionCycle(String transactionCycle) {
        return GEOGRAPHY1;
    }

    public List<String> getTransactionCyclesByOrg(String org) {
        return TRANSACTION_CYCLE1;
    }


    public static void main(String[] args) throws IOException {
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
    }
}
