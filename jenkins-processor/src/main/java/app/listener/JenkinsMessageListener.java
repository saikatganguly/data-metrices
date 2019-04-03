package app.listener;

import app.config.Channels;
import app.collector.XMLToObjectConvertor;
import app.model.BuildDetailsModel;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JenkinsMessageListener {

    @StreamListener(Channels.JENKINS_RAW_DATA)
    @SendTo(Channels.JENKINS_PROCESSED_DATA)
    public BuildDetailsModel handleMessages(@Payload String buildInfo) {
        try {
            return XMLToObjectConvertor.buildJenkinsDetails(buildInfo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
