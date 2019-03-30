package app.sender;

import com.offbytwo.jenkins.model.BuildWithDetails;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JenkinsMessageSender implements ItemWriter<String> {


    @Autowired
    private OutputStream outputStream;

    public void write(List<? extends String > list) {
        MessageChannel messageChannel = outputStream.outboundChannel();
            list.parallelStream().forEach(b -> {
                messageChannel.send(MessageBuilder
                            .withPayload(b)
                            .build());


            });

    }
}
