package app.receiver;

import app.config.InputStream;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class JenkinsMesageReceiver {

    @Autowired
    private MongoTemplate mongoTemplate;

    @StreamListener(InputStream.INPUT)
    public void handleMessages(@Payload String buildInfo) {
        System.out.println("Build info : "+buildInfo);
        Document doc = Document.parse(buildInfo);
        mongoTemplate.insert(doc, "test1");
    }
}
