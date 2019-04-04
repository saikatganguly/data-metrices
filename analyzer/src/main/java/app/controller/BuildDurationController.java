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

import static java.util.stream.Collectors.toList;
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

    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}/{repoId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForRepo(@PathVariable String transactionCycleId,
                                                    @PathVariable String geographyId,
                                                    @PathVariable String projectId,
                                                    @PathVariable String repoId,
                                                    @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                    @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        Map<String, Double> response = new HashMap<>();

        Repo repo = referenceDataService.getRepo(repoId);

        double average = repository
                .findByRepoAndDateBetween(repo.getUrl(), fromDate, toDate)
                .stream()
                .map(view -> view.getDuration())
                .mapToDouble(i -> i)
                .average()
                .getAsDouble();

        response.put(repoId, average);
        return response;
    }

    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}/{repoId}/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Long> buildDurationsForRepo(@PathVariable String transactionCycleId,
                                            @PathVariable String geographyId,
                                            @PathVariable String projectId,
                                            @PathVariable String repoId,
                                            @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                            @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {

        Repo repo = referenceDataService.getRepo(repoId);
        return repository
                .findByRepoAndDateBetween(repo.getUrl(), fromDate, toDate)
                .stream()
                .map(view -> view.getDuration())
                .collect(toList());
    }

    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForProject(@PathVariable String transactionCycleId,
                                                       @PathVariable String geographyId,
                                                       @PathVariable String projectId,
                                                       @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                       @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        List<String> repoIds = referenceDataService.getReposByProject(transactionCycleId, geographyId, projectId)
                .stream()
                .map(Repo::getId)
                .collect(toList());

        Map<String, Double> response = new HashMap<>();
        repoIds.forEach(repoId -> {
            Double average = buildDurationForRepo(transactionCycleId, geographyId, projectId, repoId, fromDate, toDate).get(repoId);
            response.put(repoId, average);
        });

        return response;
    }

    @RequestMapping("/org/{transactionCycleId}/{geographyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForGeography(@PathVariable String transactionCycleId,
                                                         @PathVariable String geographyId,
                                                         @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                         @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        List<String> projectIds = referenceDataService.getProjectsByGeography(transactionCycleId, geographyId)
                .stream()
                .map(Project::getId)
                .collect(toList());

        Map<String, Double> response = new HashMap<>();

        projectIds.forEach(projectId -> {
            double projectAverage = buildDurationForProject(transactionCycleId, geographyId, projectId, fromDate, toDate)
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .average()
                    .getAsDouble();
            response.put(projectId, projectAverage);
        });

        return response;
    }

    @RequestMapping("/org/{transactionCycleId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForTransactionCycle(@PathVariable String transactionCycleId,
                                                                @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                                @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        List<String> geographyIds = referenceDataService.getGeographyByTransactionCycle(transactionCycleId)
                .stream()
                .map(Geography::getId)
                .collect(toList());

        Map<String, Double> response = new HashMap<>();

        geographyIds.forEach(geographyId -> {
            double geographyAverage = buildDurationForGeography(transactionCycleId, geographyId, fromDate, toDate)
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .average()
                    .getAsDouble();
            response.put(geographyId, geographyAverage);
        });

        return response;
    }

    @RequestMapping("/org")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Double> buildDurationForOrganization(@RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                            @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {
        List<String> transactionCycleIds = referenceDataService.getTransactionCyclesByOrganization("org")
                .stream()
                .map(TransactionCycle::getId)
                .collect(toList());

        Map<String, Double> response = new HashMap<>();

        for (String transactionCycleId : transactionCycleIds) {
            double transactionCycleAverage = buildDurationForTransactionCycle(transactionCycleId, fromDate, toDate)
                    .values()
                    .stream()
                    .mapToDouble(i -> i)
                    .average()
                    .getAsDouble();
            response.put(transactionCycleId, transactionCycleAverage);
        }

        return response;
    }
}
