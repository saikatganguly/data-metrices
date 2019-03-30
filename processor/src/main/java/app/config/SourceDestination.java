package app.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface SourceDestination {
    String OUTPUT = "message-out";
    String INPUT = "jenkins-raw-data";

    @Output(OUTPUT)
    MessageChannel outboundChannel();

    @Input(INPUT)
    MessageChannel inbountChannel();
}
