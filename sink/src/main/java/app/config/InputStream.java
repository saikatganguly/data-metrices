package app.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface InputStream {
    String INPUT = "message-in";

    @Input(INPUT)
    SubscribableChannel inboundChannel();

}
