ackage app.sender;

import com.offbytwo.jenkins.model.BuildWithDetails;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JenkinsMessageSender implements ItemWriter<String> {

    @SendTo(SourceDestination.OUTPUT)
    public String enrichLogMessage(String log) {
        return log;
    }
}