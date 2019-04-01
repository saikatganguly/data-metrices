package app.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Topics {

    String JENKINS_RAW_DATA = "jenkins-raw-data";

    @Output(JENKINS_RAW_DATA)
    MessageChannel outboundChannel();
}
