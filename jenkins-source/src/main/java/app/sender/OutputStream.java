package app.sender;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputStream {

    String OUTPUT = "jenkins-raw-data";

    @Output(OUTPUT)
    MessageChannel outboundChannel();
}
