package app.listener;

import org.springframework.stereotype.Component;

@Component
public class JenkinsMessageListner {

//    @StreamListener(SourceDestination.INPUT)
//    @SendTo(SourceDestination.OUTPUT)
//    public Map<String, List<BuildDetailsModel>> handleMessages(@Payload String buildInfo) {
//        try {
//            return JsonToObjectConvertor.buildJenkinsDetails(buildInfo);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
}
