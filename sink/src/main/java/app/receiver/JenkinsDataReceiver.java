package app.receiver;

import app.config.Channels;
import app.model.BuildDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class JenkinsDataReceiver {
    @Autowired
    private MongoTemplate mongoTemplate;

    @StreamListener(Channels.JENKINS_PROCESSED_DATA)
    public void handleMessages(@Payload BuildDetailsModel buildInfo) {
        System.out.println("Build info : " + buildInfo);
        Query query= Query.query(Criteria.where("repoName").is(buildInfo.getGitDetails().getRepo()));
        mongoTemplate.upsert(query, new Update().push("builds", buildInfo), "jenkins_collection");
        //mongoTemplate.insert(buildInfo, "jenkins-processed-data");
    }
}
