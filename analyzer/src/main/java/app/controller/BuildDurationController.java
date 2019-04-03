package app.controller;

import app.reference.ReferenceDataService;
import app.reference.pojo.Geography;
import app.reference.pojo.Project;
import app.reference.pojo.Repo;
import app.reference.pojo.TransactionCycle;
import app.repository.BuildDurationViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;


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

    @RequestMapping("/reference-data/{projectName}")
    public List<String> referenceData(@PathVariable(name = "projectName") String projectName) {
        return referenceDataService.getReposByProject(projectName)
                .stream()
                .map(Repo::getUrl)
                .collect(Collectors.toList());
    }

    @RequestMapping("/org/{transactionCycle}/{geography}/{project}/{repo}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForRepo(@PathVariable String transactionCycle,
                                                    @PathVariable String geography,
                                                    @PathVariable String project,
                                                    @PathVariable String repo,
                                                    @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                    @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        Map<String, Double> response = new HashMap<>();

        //repo = "http://192.168.99.100:7990/scm/test-project-key-1/test-repository-1.git";

        double average = repository
                .findByRepoAndDateBetween(repo, fromDate, toDate)
                .stream()
                .map(view -> view.getDuration())
                .mapToDouble(i -> i)
                .average()
                .getAsDouble();

        response.put(repo, average);
        return response;
    }

    @RequestMapping("/org/{transactionCycle}/{geography}/{project}/{repo}/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Long> buildDurationsForRepo(@PathVariable String transactionCycle,
                                            @PathVariable String geography,
                                            @PathVariable String project,
                                            @PathVariable String repo,
                                            @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                            @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {

        return repository
                .findByRepoAndDateBetween(repo, fromDate, toDate)
                .stream()
                .map(view -> view.getDuration())
                .collect(Collectors.toList());
    }

    @RequestMapping("/org/{transactionCycle}/{geography}/{project}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForProject(@PathVariable String transactionCycle,
                                                       @PathVariable String geography,
                                                       @PathVariable String project,
                                                       @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                       @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        List<String> repos = referenceDataService.getReposByProject(project)
                .stream()
                .map(Repo::getUrl)
                .collect(Collectors.toList());

        Map<String, Double> response = new HashMap<>();
        for (String repo : repos) {
            Double average = buildDurationForRepo(transactionCycle, geography, project, repo, fromDate, toDate).get(repo);
            response.put(repo, average);
        }

        return response;
    }

    @RequestMapping("/org/{transactionCycle}/{geography}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForGeography(@PathVariable String transactionCycle,
                                                         @PathVariable String geography,
                                                         @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                         @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        List<String> projects = referenceDataService.getProjectsByGeography(geography)
                .stream()
                .map(Project::getProjectName)
                .collect(Collectors.toList());

        Map<String, Double> response = new HashMap<>();

        for (String project : projects) {
            Map<String, Double> reposAverage = buildDurationForProject(transactionCycle, geography, project, fromDate, toDate);
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
                                                                @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                                @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        List<String> geographys = referenceDataService.getGeographyByTransactionCycle(transactionCycle)
                .stream()
                .map(Geography::getGeographyName)
                .collect(Collectors.toList());

        Map<String, Double> response = new HashMap<>();

        for (String geography : geographys) {
            Map<String, Double> reposAverage = buildDurationForGeography(transactionCycle, geography, fromDate, toDate);
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
    public Map<String, Double> buildDurationForOrganization(@RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                            @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        List<String> transactionCycles = referenceDataService.getTransactionCyclesByOrganization("org")
                .stream()
                .map(TransactionCycle::getTransactionCycleName)
                .collect(Collectors.toList());

        Map<String, Double> response = new HashMap<>();

        for (String transactionCycle : transactionCycles) {
            Map<String, Double> reposAverage = buildDurationForTransactionCycle(transactionCycle, fromDate, toDate);
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
