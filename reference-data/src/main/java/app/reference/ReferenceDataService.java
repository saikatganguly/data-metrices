package app.reference;

import app.reference.pojo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;

@Service
public class ReferenceDataService {

    @Value("user-provided-data.json")
    private ClassPathResource userProvidedDataFile;

    private Organization userProvidedData;

    @PostConstruct
    public void init() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        ObjectMapper mapper = new ObjectMapper();
        try {
            userProvidedData = mapper.readValue(userProvidedDataFile.getFile(), Organization.class);
            //createData();
        } catch (IOException e) {
            System.out.println("Exception occured: " + e);
        }

        //System.out.println("Organization Data: " + userProvidedData);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public List<Repo> getReposByProject(String transactionCycle, String geography, String project) {
        if (isNull(project) || project.isEmpty()) {
            return emptyList();
        }
        for (TransactionCycle tc : userProvidedData.getTransactionCycles()) {
            if (tc.getTransactionCycleName().equalsIgnoreCase(transactionCycle)) {
                for (Geography geography1 : tc.getGeographies()) {
                    if (geography1.getGeographyName().equalsIgnoreCase(geography)) {
                        for (Project proj : geography1.getProjects()) {
                            if (proj.getProjectName().equalsIgnoreCase(project)) {
                                return proj.getRepos();
                            }
                        }
                    }
                }
            }
        }

        return emptyList();
    }


    public List<Repo> getReposByProject(String project) {
        if (isNull(project) || project.isEmpty()) {
            return emptyList();
        }
        for (TransactionCycle tc : userProvidedData.getTransactionCycles()) {
            for (Geography geography1 : tc.getGeographies()) {
                for (Project proj : geography1.getProjects()) {
                    if (proj.getProjectName().equalsIgnoreCase(project)) {
                        return proj.getRepos();
                    }
                }
            }
        }

        return emptyList();
    }

    public Repo getRepo(String repoName) {
        if (isNull(repoName) || repoName.isEmpty()) {
            return null;
        }

        for (TransactionCycle transactionCycle : userProvidedData.getTransactionCycles()) {
            for (Geography geography : transactionCycle.getGeographies()) {
                for (Project proj : geography.getProjects()) {
                    for (Repo repo : proj.getRepos()) {
                        if (repo.getName().equalsIgnoreCase(repoName)) {
                            return repo;
                        }
                    }
                }
            }
        }

        return null;
    }

    public List<Project> getProjectsByGeography(String transactionCycle, String geography) {
        if (isNull(geography) || geography.isEmpty()) {
            return emptyList();
        }
        for (TransactionCycle tc : userProvidedData.getTransactionCycles()) {
            if (tc.getTransactionCycleName().equalsIgnoreCase(transactionCycle)) {
                for (Geography geo : tc.getGeographies()) {
                    if (geo.getGeographyName().equalsIgnoreCase(geography)) {
                        return geo.getProjects();
                    }
                }
            }
        }
        return emptyList();
    }

    public List<Geography> getGeographyByTransactionCycle(String transactionCycle) {
        if (isNull(transactionCycle) || transactionCycle.isEmpty()) {
            return emptyList();
        }
        for (TransactionCycle tc : userProvidedData.getTransactionCycles()) {
            if (tc.getTransactionCycleName().equalsIgnoreCase(transactionCycle)) {
                return tc.getGeographies();
            }
        }
        return emptyList();
    }

    public List<TransactionCycle> getTransactionCyclesByOrganization(String organization) {
        if (isNull(organization) || organization.isEmpty()) {
            return emptyList();
        }
        if (userProvidedData.getOrganizationName().equalsIgnoreCase(organization)) {
            return userProvidedData.getTransactionCycles();
        }
        return emptyList();
    }


    public void createData() throws IOException {
        int count = 101;
        List<BuildDetailsModel> buildDetailsModels = new ArrayList<>();

        for (Repo repo : getRepos()) {
            for (int i = 0; i <= getRandomNumberInRange(10, 15); i++) {
                BuildDetailsModel model = getBuildDetailsModel(repo, Integer.toString(count));
                buildDetailsModels.add(model);
                count++;
            }
        }

        DataCreationRequest request = new DataCreationRequest();
        request.setBuildDetailsModels(buildDetailsModels);

        System.out.println("+++++++++++++++++++Generated Data+++++++++++++++++++++++++");
        System.out.println(request);
        System.out.println("+++++++++++++++++++Done Data+++++++++++++++++++++++++");

        ObjectMapper om = new ObjectMapper();
        om.writeValue(new File("raje-is-chu.json"), request);

    }

    private List<Repo> getRepos() {
        List<Repo> repos = new ArrayList<>();

        for (TransactionCycle transactionCycle : userProvidedData.getTransactionCycles()) {
            for (Geography geography : transactionCycle.getGeographies()) {
                for (Project proj : geography.getProjects()) {
                    for (Repo repo : proj.getRepos()) {
                        repos.add(repo);
                    }
                }
            }
        }

        return repos;
    }

    private BuildDetailsModel getBuildDetailsModel(Repo repo, String i) {
        byte[] array = new byte[7]; // length is bounded by 7

        new Random().nextBytes(array);
        String commit = UUID.randomUUID().toString();

        new Random().nextBytes(array);
        String displayName = UUID.randomUUID().toString();

        GitDetails gitDetails = new GitDetails(repo.getUrl(), "master", commit);
        CauseDetails causeDetails = new CauseDetails("some-cause", "some-user", "some-user-id", "some-upstream");
        return new BuildDetailsModel(false, "job", displayName, (long) getRandomNumberInRange(500, 800),
                (long) getRandomNumberInRange(350, 400), "executor", repo.getBuildJob(), i, true, getRandomNumberInRange(1, 100), "queue", "SUCCESS", (long) createRandomDate().getTime(), gitDetails.getRepo(), gitDetails, causeDetails);
    }

    private Date createRandomDate() {
        int day = getRandomNumberInRange(14, 28);
        int month = 3;
        int year = 119;
        return new Date(year, month, day);
    }

    private int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    public Repo getRepoFromUrl(String repoUrl) {
        if (isNull(repoUrl) || repoUrl.isEmpty()) {
            return null;
        }
        for (TransactionCycle transactionCycle : userProvidedData.getTransactionCycles()) {
            for (Geography geography : transactionCycle.getGeographies()) {
                for (Project proj : geography.getProjects()) {
                    for (Repo repo : proj.getRepos()) {
                        if (repoUrl.equalsIgnoreCase(repo.getUrl())) {
                            return repo;
                        }
                    }
                }
            }
        }
        return null;
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
