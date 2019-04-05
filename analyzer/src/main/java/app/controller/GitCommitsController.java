package app.controller;

import app.reference.ReferenceDataService;
import app.reference.pojo.Geography;
import app.reference.pojo.Project;
import app.reference.pojo.Repo;
import app.reference.pojo.TransactionCycle;
import app.repository.GitCommitsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyList;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;


@RestController
@RequestMapping("/metrices/commits")
public class GitCommitsController {

    private GitCommitsViewRepository repository;
    private ReferenceDataService referenceDataService;

    @Autowired
    public GitCommitsController(GitCommitsViewRepository repository, ReferenceDataService referenceDataService) {
        this.repository = repository;
        this.referenceDataService = referenceDataService;
    }

    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}/{repoId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitCountForRepo(@PathVariable String transactionCycleId,
                                                  @PathVariable String geographyId,
                                                  @PathVariable String projectId,
                                                  @PathVariable String repoId,
                                                  @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                  @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        Map<String, Double> response = new HashMap<>();

        Repo repo = referenceDataService.getRepo(repoId);

        double frequency = repository
                .findByRepoAndDateBetween(repo.getUrl(), fromDate, toDate)
                .stream()
                .map(view -> view.getCount())
                .mapToDouble(i -> i)
                .sum();

        response.put(repoId, frequency);
        return response;
    }

    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}/{repoId}/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Long> commitsForRepo(@PathVariable String transactionCycleId,
                                     @PathVariable String geographyId,
                                     @PathVariable String projectId,
                                     @PathVariable String repoId,
                                     @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                     @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {

        Repo repo = referenceDataService.getRepo(repoId);
        return repository
                .findByRepoAndDateBetween(repo.getUrl(), fromDate, toDate)
                .stream()
                .map(view -> view.getCount())
                .collect(toList());
    }

    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitCountForProject(@PathVariable String transactionCycleId,
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
            Double repoCommitFrequency = commitCountForRepo(transactionCycleId, geographyId, projectId, repoId, fromDate, toDate).get(repoId);
            response.put(repoId, repoCommitFrequency);
        });

        return response;
    }

    @RequestMapping("/org/{transactionCycleId}/{geographyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitCountForGeography(@PathVariable String transactionCycleId,
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
            double projectCommitFrequency = commitCountForProject(transactionCycleId, geographyId, projectId, fromDate, toDate, emptyList())
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .sum();
            response.put(projectId, projectCommitFrequency);
        });

        return response;
    }

    @RequestMapping("/org/{transactionCycleId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitCountForTransactionCycle(@PathVariable String transactionCycleId,
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
            double geographyCommitFrequency = commitCountForGeography(transactionCycleId, geographyId, fromDate, toDate, emptyList())
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .sum();
            response.put(geographyId, geographyCommitFrequency);
        });

        return response;
    }

    @RequestMapping("/org")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitCountForOrganization(@RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
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
            double transactionCycleCommitFrequency = commitCountForTransactionCycle(transactionCycleId, fromDate, toDate, emptyList())
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .sum();
            response.put(transactionCycleId, transactionCycleCommitFrequency);
        }

        return response;
    }
}
