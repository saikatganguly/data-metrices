package app.listener;

import app.config.SourceDestination;
import app.converter.SonarqubeDataCollector;
import app.model.BuildDetailsModel;
import app.reference.ReferenceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JenkinsMessageListener {

    private SonarqubeDataCollector collector;

    private MongoTemplate template;

    private ReferenceDataService referenceDataService;

    @Autowired
    public JenkinsMessageListener(SonarqubeDataCollector collector, MongoTemplate template) {
        this.collector = collector;
        this.template = template;
    }

    @StreamListener(SourceDestination.INPUT)
    @SendTo(SourceDestination.OUTPUT)
    public List<Map<String, String>> handleMessages(@Payload Map<String, List<BuildDetailsModel>> jenkinsBuildInfo) {

        List<Map<String, String>> projectsQualityInformation = new ArrayList<>();

        for (List<BuildDetailsModel> buildDetailsModels : jenkinsBuildInfo.values()) {
            for (BuildDetailsModel buildDetailsModel : buildDetailsModels) {

                //TODO : Need to fix this model
                //Currently it is getting all builds information for
                //Need to revisit
                String repoUrl = buildDetailsModel.getGitDetails().getRepo();

                //TODO: Hardcoded project name
                //Fix it to fetch project information from mapping tables
                String projectName = getProjectName(repoUrl);

                String projectQualityData = collector.collect(projectName);

                Map<String, String> projectQualityInformation = new HashMap<>();

                projectQualityInformation.put(repoUrl, projectQualityData);

                projectsQualityInformation.add(projectQualityInformation);
            }
        }

        return projectsQualityInformation;
    }

    private String getProjectName(String repoUrl) {

        //TODO: Uncomment it after having reference data in mongo
//        Query query = new Query();
//        query.addCriteria(Criteria.where("name").is("Eric"));
//        List<String> projectName = template.find(query, String.class);
//        return projectName.get(0);

        //referenceDataService.getSonarProjectKeyFor(repoUrl);

        return "TEST-SONAR-PROJECT-KEY-1";
    }
}
