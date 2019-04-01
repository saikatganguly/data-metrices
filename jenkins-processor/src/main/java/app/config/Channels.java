package app.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Channels {
    String JENKINS_RAW_DATA = "jenkins-raw-data";
    String JENKINS_PROCESSED_DATA = "jenkins-processed-data";

    @Output(JENKINS_PROCESSED_DATA)
    MessageChannel outboundChannel();

    @Input(JENKINS_RAW_DATA)
    MessageChannel inbountChannel();
}
