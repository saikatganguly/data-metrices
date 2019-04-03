package app.controller;

import app.controller.request.DataCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sink/data/")
public class DataCreationController {

    private MongoTemplate mongoTemplate;

    @Autowired
    public DataCreationController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(path = "/jenkins", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void createJenkinsData(@RequestBody DataCreationRequest request) {
        mongoTemplate.insert(request.getBuildDetailsModels(), "jenkins-processed-data");
    }

    @RequestMapping(path = "/sonar", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void createSonarData(@RequestBody DataCreationRequest request) {
        mongoTemplate.insert(request.getSonarProjectInfo(), "sonar-processed-data");
    }

    @RequestMapping(path = "/bitbucket", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void createBitbucketData(@RequestBody DataCreationRequest request) {
        mongoTemplate.insert(request.getBitbucketRepoInfo(), "bitbucket-processed-data");
    }
}
