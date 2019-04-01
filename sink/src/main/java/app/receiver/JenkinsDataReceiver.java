package app.receiver;

import app.config.InputStream;
import app.model.BuildDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JenkinsDataReceiver {
    @Autowired
    private MongoTemplate mongoTemplate;

    @StreamListener(InputStream.JENKINS_PROCESSED_DATA)
    public void handleMessages(@Payload Map<String, List<BuildDetailsModel>> buildInfo) {
        System.out.println("Build info : " + buildInfo);

        mongoTemplate.insert(buildInfo, "jenkins-processed-data");
    }
}
