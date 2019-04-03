package app.receiver;

import app.config.Channels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class SonarDataReceiver {
    @Autowired
    private MongoTemplate mongoTemplate;

    @StreamListener(Channels.SONAR_PROCESSED_DATA)
    public void handleMessages(@Payload List<Map<String, String>> sonarProjectInfo) {
        System.out.println("sonarProjectInfo = " + sonarProjectInfo);

        if (isEmpty(sonarProjectInfo)) {
            return;
        }

        mongoTemplate.insert(sonarProjectInfo, "sonar-processed-data");
    }
}
