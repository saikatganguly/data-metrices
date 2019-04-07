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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
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

    @RequestMapping("/org/{transactionCycleId}/{geographyId}/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Long> commitFrequencyForProject(@PathVariable String transactionCycleId,
                                                       @PathVariable String geographyId,
                                                       @PathVariable String projectId,
                                                       @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                       @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {

        Map<String, Long> response = new HashMap<>();

        List<String> repoIds = referenceDataService.getReposByProject(transactionCycleId, geographyId, projectId)
                .stream()
                .map(Repo::getId)
                .collect(toList());

        getByReposAndDateBetween(repoIds, fromDate, toDate)
                .stream()
                .collect(groupingBy(GitCommitsView::getDate))
                .entrySet().forEach(repos ->
                response.put(repos.getKey().toString(), getCommitFrequencyForRepos(repos.getValue()))
        );

        return response;
    }

    @RequestMapping("/org/{transactionCycleId}/{geographyId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Long> commitFrequencyForGeography(@PathVariable String transactionCycleId,
                                                         @PathVariable String geographyId,
                                                         @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                         @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {

        Map<String, Long> response = new HashMap<>();

        List<String> repoIds = referenceDataService.getReposByGeography(transactionCycleId, geographyId)
                .stream()
                .map(Repo::getId)
                .collect(toList());

        getByReposAndDateBetween(repoIds, fromDate, toDate)
                .stream()
                .collect(groupingBy(GitCommitsView::getDate))
                .entrySet().forEach(repos ->
                response.put(repos.getKey().toString(), getCommitFrequencyForRepos(repos.getValue()))
        );

        return response;
    }

    @RequestMapping("/org/{transactionCycleId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Long> commitFrequencyForTransactionCycle(@PathVariable String transactionCycleId,
                                                                @RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                                @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {

        Map<String, Long> response = new HashMap<>();

        List<String> repoIds = referenceDataService.getReposByTransactionCycle(transactionCycleId)
                .stream()
                .map(Repo::getId)
                .collect(toList());

        getByReposAndDateBetween(repoIds, fromDate, toDate)
                .stream()
                .collect(groupingBy(GitCommitsView::getDate))
                .entrySet().forEach(repos ->
                response.put(repos.getKey().toString(), getCommitFrequencyForRepos(repos.getValue()))
        );

        return response;
    }

    @RequestMapping("/org")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Long> commitFrequencyForOrg(@RequestParam @DateTimeFormat(iso = DATE) Date fromDate,
                                                   @RequestParam @DateTimeFormat(iso = DATE) Date toDate) {

        Map<String, Long> response = new HashMap<>();

        List<String> repoIds = referenceDataService.getAllRepos()
                .stream()
                .map(Repo::getId)
                .collect(toList());

        getByReposAndDateBetween(repoIds, fromDate, toDate)
                .stream()
                .collect(groupingBy(GitCommitsView::getDate))
                .entrySet().forEach(repos ->
                response.put(repos.getKey().toString(), getCommitFrequencyForRepos(repos.getValue()))
        );
        return response;
    }

    private List<GitCommitsView> getByRepoAndDateBetween(Date fromDate, Date toDate, Repo repo) {
        return repository
                .findByRepoAndDateBetween(repo.getUrl(), fromDate, toDate);
    }

    private List<GitCommitsView> getByReposAndDateBetween(List<String> repoUrls, Date fromDate, Date toDate) {
        return repository
                .findByRepoInAndDateBetween(repoUrls, fromDate, toDate);
    }

    private long getCommitFrequencyForRepos(List<GitCommitsView> repos) {
        return repos.stream().mapToLong(i -> i.getCount()).sum();
    }
}
