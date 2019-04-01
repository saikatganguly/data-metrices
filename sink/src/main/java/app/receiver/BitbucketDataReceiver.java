package app.receiver;

import app.config.Channels;
import app.model.BitbucketRepo;
import app.model.CommitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BitbucketDataReceiver {
    @Autowired
    private MongoTemplate mongoTemplate;

    @StreamListener(Channels.BITBUCKET_PROCESSED_DATA)
    public void handleMessages(@Payload List<Map<BitbucketRepo, Map<String, CommitInfo>>> bitbucketRepoInfo) {
        System.out.println("bitbucketRepoInfo = " + bitbucketRepoInfo);

        mongoTemplate.insert(bitbucketRepoInfo, "bitbucket-processed-data");
    }
}
