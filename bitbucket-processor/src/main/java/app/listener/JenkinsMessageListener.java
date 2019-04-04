package app.listener;

import app.collector.BitbucketCollector;
import app.config.Channels;
import app.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class JenkinsMessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(JenkinsMessageListener.class);

    private BitbucketCollector collector;
    private MongoTemplate mongoTemplate;

    @Autowired
    public JenkinsMessageListener(BitbucketCollector collector, MongoTemplate mongoTemplate) {
        this.collector = collector;
        this.mongoTemplate = mongoTemplate;
    }

    @StreamListener(Channels.JENKINS_PROCESSED_DATA)
    @SendTo(Channels.BITBUCKET_PROCESSED_DATA)
    public List<Map<String, Map<String, CommitInfo>>> handleMessages(@Payload BuildDetailsModel buildDetailsModel) throws MalformedURLException {

        List<Map<String, Map<String, CommitInfo>>> buildsRepoInformationList = new ArrayList<>();

        GitDetails gitDetails = buildDetailsModel.getGitDetails();
        String repoUrl = gitDetails.getRepo();

        String since = getLastSyncedCommit(repoUrl);

        String until = getBuildCommit(buildDetailsModel);

        updateLastSyncedCommit(gitDetails);

        Map<String, Map<String, CommitInfo>> repoInformation = new HashMap<>();
        Map<String, CommitInfo> commitInfoMap = getRepoInformation(repoUrl, since, until);

        if (isEmpty(commitInfoMap)) {
            LOG.warn("No commit information available for repo {}", repoUrl);
        } else {
            repoInformation.put(repoUrl, commitInfoMap);

            repoInformation
                    .entrySet()
                    .stream()
                    .forEach(
                            entry -> {
                                entry.getValue()
                                        .entrySet()
                                        .stream()
                                        .forEach(entryConsumer -> System.out.println(entryConsumer.getValue()));
                            }
                    );
            buildsRepoInformationList.add(repoInformation);
        }

        return buildsRepoInformationList;
    }

    private String getBuildCommit(BuildDetailsModel buildDetailsModel) {
        return buildDetailsModel.getGitDetails().getCommit();
    }

    private String getLastSyncedCommit(String repoUrl) {
        Query query = new Query(Criteria.where("repoUrl").is(repoUrl));
        LastCommitTracker tracker = mongoTemplate.findOne(query, LastCommitTracker.class, "bitbucket-commit-tracker-collection");

        if (StringUtils.isEmpty(tracker.getCommitId())) {
            return "";
        }
        return tracker.getCommitId();
    }

    private void updateLastSyncedCommit(GitDetails gitDetails) {
        Query query = new Query();
        query.addCriteria(Criteria.where("repoUrl").is(gitDetails.getRepo()));
        Update update = new Update();
        update.set("commitId", gitDetails.getCommit());
        mongoTemplate.upsert(query, update, LastCommitTracker.class, "bitbucket-commit-tracker-collection");
    }

    public Map<String, CommitInfo> getRepoInformation(String repoUrl, String since, String until) throws MalformedURLException {
        URL url = new URL(repoUrl);
        String hostName = url.getProtocol() + "://" + url.getAuthority();

        String substring = repoUrl.substring(0, repoUrl.indexOf(".git"));

        String[] splitRepoUrl = substring.split("/");
        int length = splitRepoUrl.length;

        String projectName = splitRepoUrl[length - 2];
        String repoName = splitRepoUrl[length - 1];

        BitbucketRepo bitbucketRepo = new BitbucketRepo(hostName, projectName, repoName);
        return collector.repoInformation(bitbucketRepo, since, until);
    }
}
