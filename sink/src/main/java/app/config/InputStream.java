package app.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InputStream {
    String INPUT = "jenkins-raw-data";

    @Input(INPUT)
    SubscribableChannel inboundChannel();

}
