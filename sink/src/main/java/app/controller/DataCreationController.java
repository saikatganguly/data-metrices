package app.controller;

import app.controller.request.DataCreationRequest;
import app.model.BuildDetailsModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@RestController
@RequestMapping("/sink/data/")
public class DataCreationController {

    private MongoTemplate mongoTemplate;

    @Value("input-file.json")
    private ClassPathResource inputFIle;


    @Autowired
    public DataCreationController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @RequestMapping(path = "/jenkins", method = RequestMethod.POST, produces = APPLICATION_JSON_UTF8_VALUE, consumes = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void createJenkinsData() {

        ObjectMapper om = new ObjectMapper();
        try {
            DataCreationRequest dataCreationRequest = om.readValue(inputFIle.getFile(), DataCreationRequest.class);

            for (BuildDetailsModel buildDetailsModel : dataCreationRequest.getBuildDetailsModels()) {
                mongoTemplate.insert(buildDetailsModel);
            }
        } catch (IOException e) {
            System.out.println("Exception occured: " + e);
        }

    }
/*
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
    }*/
}
