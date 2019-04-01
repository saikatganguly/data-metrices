package app.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InputStream {
    String JENKINS_PROCESSED_DATA = "jenkins-processed-data";

    @Input(JENKINS_PROCESSED_DATA)
    SubscribableChannel inboundChannel();

}
