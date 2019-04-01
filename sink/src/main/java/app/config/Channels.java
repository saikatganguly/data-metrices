package app.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Channels {
    String JENKINS_PROCESSED_DATA = "jenkins-processed-data";
    String BITBUCKET_PROCESSED_DATA = "bitbucket-processed-data";
    String SONAR_PROCESSED_DATA = "sonar-processed-data";

    @Input(JENKINS_PROCESSED_DATA)
    SubscribableChannel jenkinsInboundChannel();

    @Input(BITBUCKET_PROCESSED_DATA)
    SubscribableChannel bitbucketInboundChannel();

    @Input(SONAR_PROCESSED_DATA)
    SubscribableChannel sonarInboundChannel();

}
