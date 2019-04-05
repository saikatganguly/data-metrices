package app.controller;

import app.entity.GitCommitsView;
import app.reference.ReferenceDataService;
import app.reference.pojo.Repo;
import app.repository.GitCommitsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;


@RestController
@RequestMapping("/metrices/commit-frequency")
public class GitCommitFrequencyController {

    private GitCommitsViewRepository repository;
    private ReferenceDataService referenceDataService;

    @Autowired
    public GitCommitFrequencyController(GitCommitsViewRepository repository, ReferenceDataService referenceDataService) {
        this.repository = repository;
        this.referenceDataService = referenceDataService;
    }

   /* @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}/{repoId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForRepo(@PathVariable String transactionCycleId,
                                                      @PathVariable String geographyId,
                                                      @PathVariable String projectId,
                                                      @PathVariable String repoId,
                                                      @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                      @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        Map<String, Double> response = new HashMap<>();

        Repo repo = referenceDataService.getRepo(repoId);

        double frequency = getByRepoAndDateBetween(fromDate, toDate, repo)
                .stream()
                .map(view -> view.getCount())
                .mapToDouble(i -> i)
                .sum();

        response.put(repoId, frequency);
        return response;
    }*/

    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}/{repoId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Long> commitFrequenciesForRepo(@PathVariable String transactionCycleId,
                                               @PathVariable String geographyId,
                                               @PathVariable String projectId,
                                               @PathVariable String repoId,
                                               @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                               @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {

        Repo repo = referenceDataService.getRepo(repoId);
        return getByRepoAndDateBetween(fromDate, toDate, repo)
                .stream()
                .map(view -> view.getCount())
                .collect(toList());
    }

    private List<GitCommitsView> getByRepoAndDateBetween(@RequestParam @DateTimeFormat(iso = DATE) Date fromDate, @RequestParam @DateTimeFormat(iso = DATE) Date toDate, Repo repo) {
        return repository
                .findByRepoAndDateBetween(repo.getUrl(), fromDate, toDate);
    }

/*    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForProject(@PathVariable String transactionCycleId,
                                                         @PathVariable String geographyId,
                                                         @PathVariable String projectId,
                                                         @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                         @RequestParam @DateTimeFormat(iso = DATE) Date toDate,
                                                         @RequestParam(required = false) List<String> repoIds) {


        if (isNull(repoIds) || repoIds.isEmpty()) {
            repoIds = referenceDataService.getReposByProject(transactionCycleId, geographyId, projectId)
                    .stream()
                    .map(Repo::getId)
                    .collect(toList());
        }

        Map<String, Double> response = new HashMap<>();
        repoIds.forEach(repoId -> {
            Double repoCommitFrequency = commitFrequencyForRepo(transactionCycleId, geographyId, projectId, repoId, fromDate, toDate).get(repoId);
            response.put(repoId, repoCommitFrequency);
        });

        return response;
    }*/

    /*@RequestMapping("/org/{transactionCycleId}/{geographyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForGeography(@PathVariable String transactionCycleId,
                                                           @PathVariable String geographyId,
                                                           @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                           @RequestParam @DateTimeFormat(iso = DATE) Date toDate,
                                                           @RequestParam(required = false) List<String> projectIds) {
        if (isNull(projectIds) || projectIds.isEmpty()) {
            projectIds = referenceDataService.getProjectsByGeography(transactionCycleId, geographyId)
                    .stream()
                    .map(Project::getId)
                    .collect(toList());
        }

        Map<String, Double> response = new HashMap<>();

        projectIds.forEach(projectId -> {
            double projectCommitFrequency = commitFrequencyForProject(transactionCycleId, geographyId, projectId, fromDate, toDate, emptyList())
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .sum();
            response.put(projectId, projectCommitFrequency);
        });

        return response;
    }*/

    /*@RequestMapping("/org/{transactionCycleId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForTransactionCycle(@PathVariable String transactionCycleId,
                                                                  @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                                  @RequestParam @DateTimeFormat(iso = DATE) Date toDate,
                                                                  @RequestParam(required = false) List<String> geographyIds) {
        if (isNull(geographyIds) || geographyIds.isEmpty()) {
            geographyIds = referenceDataService.getGeographyByTransactionCycle(transactionCycleId)
                    .stream()
                    .map(Geography::getId)
                    .collect(toList());
        }

        Map<String, Double> response = new HashMap<>();

        geographyIds.forEach(geographyId -> {
            double geographyCommitFrequency = commitFrequencyForGeography(transactionCycleId, geographyId, fromDate, toDate, emptyList())
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .sum();
            response.put(geographyId, geographyCommitFrequency);
        });

        return response;
    }*/

    /*@RequestMapping("/org")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForOrganization(@RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                              @RequestParam @DateTimeFormat(iso = DATE) Date toDate,
                                                              @RequestParam(required = false) List<String> transactionCycleIds) {
        if (isNull(transactionCycleIds) || transactionCycleIds.isEmpty()) {
            transactionCycleIds = referenceDataService.getTransactionCyclesByOrganization("org")
                    .stream()
                    .map(TransactionCycle::getId)
                    .collect(toList());
        }

        Map<String, Double> response = new HashMap<>();

        for (String transactionCycleId : transactionCycleIds) {
            double transactionCycleCommitFrequency = commitFrequencyForTransactionCycle(transactionCycleId, fromDate, toDate, emptyList())
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .sum();
            response.put(transactionCycleId, transactionCycleCommitFrequency);
        }

        return response;
    }*/
}
