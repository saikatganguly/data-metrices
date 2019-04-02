package app.controller;

import app.controller.request.DateRange;
import app.entity.BuildDurationView;
import app.reference.ReferenceData;
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

    private ReferenceData referenceData;
    private BuildDurationViewRepository repository;

    @Autowired
    public BuildDurationController(ReferenceData referenceData, BuildDurationViewRepository repository) {
        this.referenceData = referenceData;
        this.repository = repository;
    }

    @RequestMapping("/barclays/{transactionCycle}/{geography}/{project}/{repo}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Long> buildDurationForRepo(@PathVariable String transactionCycle,
                                                  @PathVariable String geography,
                                                  @PathVariable String project,
                                                  @PathVariable String repo,
                                                  @RequestBody DateRange range) {
        Map<String, Long> response = new HashMap<>();
        response.put(repo, repository.getAverageOfDurationByRepoAndDateBetween(repo, range.getFrom(), range.getTo()));
        return response;
    }

    @RequestMapping("/barclays/{transactionCycle}/{geography}/{project}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Long> buildDurationForRepos(@PathVariable String transactionCycle,
                                                         @PathVariable String geography,
                                                         @PathVariable String project,
                                                         @RequestBody DateRange range) {
        List<String> repos = referenceData.getReposByProject(project);
        Map<String, Long> response = new HashMap<>();
        for (String repo:repos) {
            response.put(repo,buildDurationForRepo(transactionCycle,geography,project,repo,range).get(repo));
        }

        return response;
    }

    @RequestMapping("/barclays/{transactionCycle}/{geography}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BuildDurationView> buildDurationForProject(@PathVariable String transactionCycle,
                                                           @PathVariable String geography,
                                                           @RequestBody DateRange range) {
        return null;
    }

    @RequestMapping("/barclays/{transactionCycle}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BuildDurationView> buildDurationForGeography(@PathVariable String transactionCycle,
                                                             @PathVariable String geography,
                                                             @RequestBody DateRange range) {
        return null;
    }

    @RequestMapping("/barclays")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BuildDurationView> buildDurationForTransactionCycle(@PathVariable String transactionCycle,
                                                                    @PathVariable String geography,
                                                                    @RequestBody DateRange range) {
        return null;
    }
}
