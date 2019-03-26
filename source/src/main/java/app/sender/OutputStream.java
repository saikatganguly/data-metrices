package app.sender;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface OutputStream {

    String OUTPUT = "message-out";

    @Output(OUTPUT)
    MessageChannel outboundChannel();
}
