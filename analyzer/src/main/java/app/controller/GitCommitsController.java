package app.controller;

import app.controller.request.DateRange;
import app.reference.ReferenceDataService;
import app.reference.pojo.Geography;
import app.reference.pojo.Project;
import app.reference.pojo.Repo;
import app.reference.pojo.TransactionCycle;
import app.repository.GitCommitsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/metrices/commit/frequency")
public class GitCommitsController {

    private GitCommitsViewRepository repository;
    private ReferenceDataService referenceDataService;

    @Autowired
    public GitCommitsController(GitCommitsViewRepository repository, ReferenceDataService referenceDataService) {
        this.repository = repository;
        this.referenceDataService = referenceDataService;
    }

    @RequestMapping("/org/{transactionCycle}/{geography}/{project}/{repo}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForRepo(@PathVariable String transactionCycle,
                                                      @PathVariable String geography,
                                                      @PathVariable String project,
                                                      @PathVariable String repo,
                                                      @RequestBody DateRange range) {
        Map<String, Double> response = new HashMap<>();
        response.put(repo, repository.getAverageOfDurationByRepoAndDateBetween(repo, range.getFrom(), range.getTo()));
        return response;
    }

    @RequestMapping("/org/{transactionCycle}/{geography}/{project}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForProject(@PathVariable String transactionCycle,
                                                         @PathVariable String geography,
                                                         @PathVariable String project,
                                                         @RequestBody DateRange range) {
        List<String> repos = referenceDataService.getReposByProject(project)
                .stream()
                .map(Repo::getUrl)
                .collect(Collectors.toList());

        Map<String, Double> response = new HashMap<>();
        for (String repo : repos) {
            response.put(repo, commitFrequencyForRepo(transactionCycle, geography, project, repo, range).get(repo));
        }

        return response;
    }

    @RequestMapping("/org/{transactionCycle}/{geography}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForGeography(@PathVariable String transactionCycle,
                                                           @PathVariable String geography,
                                                           @RequestBody DateRange range) {
        List<String> projects = referenceDataService.getProjectsByGeography(geography)
                .stream()
                .map(Project::getProjectName)
                .collect(Collectors.toList());

        Map<String, Double> response = new HashMap<>();

        for (String project : projects) {
            Map<String, Double> reposAverage = commitFrequencyForProject(transactionCycle, geography, project, range);
            double average = reposAverage
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .average()
                    .getAsDouble();
            response.put(project, average);
        }

        return response;
    }

    @RequestMapping("/org/{transactionCycle}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForTransactionCycle(@PathVariable String transactionCycle,
                                                                  @RequestBody DateRange range) {
        List<String> geographys = referenceDataService.getGeographyByTransactionCycle(transactionCycle)
                .stream()
                .map(Geography::getGeographyName)
                .collect(Collectors.toList());

        Map<String, Double> response = new HashMap<>();

        for (String geography : geographys) {
            Map<String, Double> reposAverage = commitFrequencyForGeography(transactionCycle, geography, range);
            double average = reposAverage
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .average()
                    .getAsDouble();
            response.put(geography, average);
        }

        return response;
    }

    @RequestMapping("/org")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> commitFrequencyForOrganization(@RequestBody DateRange range) {
        List<String> transactionCycles = referenceDataService.getTransactionCyclesByOrganization("org")
                .stream()
                .map(TransactionCycle::getTransactionCycleName)
                .collect(Collectors.toList());

        Map<String, Double> response = new HashMap<>();

        for (String transactionCycle : transactionCycles) {
            Map<String, Double> reposAverage = commitFrequencyForTransactionCycle(transactionCycle, range);
            double average = reposAverage
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .average()
                    .getAsDouble();
            response.put(transactionCycle, average);
        }

        return response;
    }
}
