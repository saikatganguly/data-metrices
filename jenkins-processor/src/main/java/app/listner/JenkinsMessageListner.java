package app.listner;

import app.config.SourceDestination;
import app.converter.JsonToObjectConvertor;
import app.model.BuildDetailsModel;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class JenkinsMessageListner {

    @StreamListener(SourceDestination.INPUT)
    @SendTo(SourceDestination.OUTPUT)
    public Map<String, List<BuildDetailsModel>> handleMessages(@Payload String buildInfo) {
        try {
            return JsonToObjectConvertor.buildJenkinsDetails(buildInfo);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
