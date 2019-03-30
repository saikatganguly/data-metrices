package app.receiver;

import app.config.InputStream;
import app.converter.JsonToObjectConvertor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class JenkinsMesageReceiver {
    @Autowired
    private MongoTemplate mongoTemplate;

    private ObjectMapper mapper = new ObjectMapper();

    @StreamListener(InputStream.INPUT)
    public void handleMessages(@Payload String completeJenkinsBuildInfo) {
        String processedJenkinsBuildInfo = "";
        try {
            processedJenkinsBuildInfo = mapper.writeValueAsString(JsonToObjectConvertor.buildJenkinsDetails(completeJenkinsBuildInfo));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mongoTemplate.insert(processedJenkinsBuildInfo, "test1");
    }
}
