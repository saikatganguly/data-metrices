package app.controller;

import app.controller.request.DateRange;
import app.reference.ReferenceDataService;
import app.repository.BuildDurationViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/metrices/build/duration")
public class BuildDurationController {

    private BuildDurationViewRepository repository;
    private ReferenceDataService referenceDataService;

    @Autowired
    public BuildDurationController(BuildDurationViewRepository repository, ReferenceDataService referenceDataService) {
        this.repository = repository;
        this.referenceDataService = referenceDataService;
    }

    @RequestMapping("/reference-data/{repoName}")
    public List<String> referenceData(@PathVariable(name = "repoName") String repoName) {
        return referenceDataService.getReposByProject(repoName);
    }

    @RequestMapping("/org/{transactionCycle}/{geography}/{project}/{repo}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForRepo(@PathVariable String transactionCycle,
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
    public Map<String, Double> buildDurationForProject(@PathVariable String transactionCycle,
                                                       @PathVariable String geography,
                                                       @PathVariable String project,
                                                       @RequestBody DateRange range) {
        List<String> repos = referenceDataService.getReposByProject(project);
        Map<String, Double> response = new HashMap<>();
        for (String repo : repos) {
            response.put(repo, buildDurationForRepo(transactionCycle, geography, project, repo, range).get(repo));
        }

        return response;
    }

    @RequestMapping("/org/{transactionCycle}/{geography}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForGeography(@PathVariable String transactionCycle,
                                                         @PathVariable String geography,
                                                         @RequestBody DateRange range) {
        List<String> projects = referenceDataService.getProjectsByGeography(geography);
        Map<String, Double> response = new HashMap<>();

        for (String project : projects) {
            Map<String, Double> reposAverage = buildDurationForProject(transactionCycle, geography, project, range);
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
    public Map<String, Double> buildDurationForTransactionCycle(@PathVariable String transactionCycle,
                                                                @RequestBody DateRange range) {
        List<String> geographys = referenceDataService.getGeographyByTransactionCycle(transactionCycle);
        Map<String, Double> response = new HashMap<>();

        for (String geography : geographys) {
            Map<String, Double> reposAverage = buildDurationForGeography(transactionCycle, geography, range);
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
    public Map<String, Double> buildDurationForOrganization(@RequestBody DateRange range) {
        List<String> transactionCycles = referenceDataService.getTransactionCyclesByOrganization("org");
        Map<String, Double> response = new HashMap<>();

        for (String transactionCycle : transactionCycles) {
            Map<String, Double> reposAverage = buildDurationForTransactionCycle(transactionCycle, range);
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
