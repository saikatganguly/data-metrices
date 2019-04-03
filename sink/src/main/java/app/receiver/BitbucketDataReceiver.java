package app.receiver;

import app.config.Channels;
import app.model.BitbucketRepo;
import app.model.CommitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class BitbucketDataReceiver {
    @Autowired
    private MongoTemplate mongoTemplate;

    @StreamListener(Channels.BITBUCKET_PROCESSED_DATA)
    public void handleMessages(@Payload List<Map<String, Map<String, CommitInfo>>> bitbucketRepoInfo) {
        System.out.println("bitbucketRepoInfo = " + bitbucketRepoInfo);

        if(isEmpty(bitbucketRepoInfo)){
            return;
        }

        mongoTemplate.insert(bitbucketRepoInfo, "bitbucket-processed-data");
    }
}
