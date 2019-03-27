package app.listner;

import app.config.SourceDestination;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class JenkinsMessageListner {

    @StreamListener(SourceDestination.INPUT)
    public void handleMessages(@Payload String buildInfo) {
        System.out.println("Build info : "+buildInfo);
    }
}
