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

        System.out.println("Organization Data: " + userProvidedData);
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public Repo getRepo(String repoId) {
        if (isNull(repoId) || repoId.isEmpty()) {
            return null;
        }

        for (TransactionCycle transactionCycle : userProvidedData.getTransactionCycles()) {
            for (Geography geography : transactionCycle.getGeographies()) {
                for (Project project : geography.getProjects()) {
                    for (Repo repo : project.getRepos()) {
                        if (repo.getId().equalsIgnoreCase(repoId)) {
                            return repo;
                        }
                    }
                }
            }
        }

        return null;
    }

    public List<Repo> getReposByProject(String transactionCycleId, String geographyId, String projectId) {
        if (isNull(projectId) || projectId.isEmpty()) {
            return emptyList();
        }
        for (TransactionCycle tc : userProvidedData.getTransactionCycles()) {
            if (tc.getId().equalsIgnoreCase(transactionCycleId)) {
                for (Geography geography : tc.getGeographies()) {
                    if (geography.getId().equalsIgnoreCase(geographyId)) {
                        for (Project project : geography.getProjects()) {
                            if (project.getId().equalsIgnoreCase(projectId)) {
                                return project.getRepos();
                            }
                        }
                    }
                }
            }
        }

        return emptyList();
    }

    public List<Project> getProjectsByGeography(String transactionCycleId, String geographyId) {
        if (isNull(geographyId) || geographyId.isEmpty()) {
            return emptyList();
        }
        for (TransactionCycle transactionCycle : userProvidedData.getTransactionCycles()) {
            if (transactionCycle.getId().equalsIgnoreCase(transactionCycleId)) {
                for (Geography geography : transactionCycle.getGeographies()) {
                    if (geography.getId().equalsIgnoreCase(geographyId)) {
                        return geography.getProjects();
                    }
                }
            }
        }
        return emptyList();
    }

    public List<Geography> getGeographyByTransactionCycle(String transactionCycleId) {
        if (isNull(transactionCycleId) || transactionCycleId.isEmpty()) {
            return emptyList();
        }
        for (TransactionCycle transactionCycle : userProvidedData.getTransactionCycles()) {
            if (transactionCycle.getId().equalsIgnoreCase(transactionCycleId)) {
                return transactionCycle.getGeographies();
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
