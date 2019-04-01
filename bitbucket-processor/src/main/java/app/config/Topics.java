package app.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Topics {
    String JENKINS_PROCESSED_DATA = "jenkins-processed-data";
    String BITBUCKET_PROCESSED_DATA = "bitbucket-processed-data";

    @Output(BITBUCKET_PROCESSED_DATA)
    MessageChannel outboundChannel();

    @Input(JENKINS_PROCESSED_DATA)
    MessageChannel inbountChannel();
}
