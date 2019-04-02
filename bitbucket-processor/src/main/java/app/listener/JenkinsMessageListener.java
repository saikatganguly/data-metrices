package app.listener;

import app.config.Channels;
import app.converter.BitbucketCollector;
import app.model.BitbucketRepo;
import app.model.BuildDetailsModel;
import app.model.CommitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JenkinsMessageListener {

    private BitbucketCollector collector;
    private MongoTemplate mongoTemplate;

    @Autowired
    public JenkinsMessageListener(BitbucketCollector collector, MongoTemplate mongoTemplate) {
        this.collector = collector;
        this.mongoTemplate = mongoTemplate;
    }

    @StreamListener(Channels.JENKINS_PROCESSED_DATA)
    @SendTo(Channels.BITBUCKET_PROCESSED_DATA)
    public List<Map<String, Map<String, CommitInfo>>> handleMessages(@Payload Map<String, List<BuildDetailsModel>> jenkinsBuildInfo) throws MalformedURLException {

        List<Map<String, Map<String, CommitInfo>>> buildsRepoInformationList = new ArrayList<>();

        for (List<BuildDetailsModel> buildDetailsModels : jenkinsBuildInfo.values()) {
            for (BuildDetailsModel buildDetailsModel : buildDetailsModels) {
                //TODO : Need to fix this model
                //Currently it is getting all builds information for
                //Need to revisit
                String repoUrl = buildDetailsModel.getGitDetails().getRepo();

                String since = getLastSyncedCommit(repoUrl);

                String until = getBuildCommit(buildDetailsModel);

                updateLastSyncedCommit(repoUrl);

                Map<String, Map<String, CommitInfo>> repoInformation = new HashMap<>();
                repoInformation.put(repoUrl, getRepoInformation(repoUrl, since, until));

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
        }

        return buildsRepoInformationList;
    }

    private String getBuildCommit(BuildDetailsModel buildDetailsModel) {
        return buildDetailsModel.getGitDetails().getCommit();
    }

    private String getLastSyncedCommit(String repoUrl) {
        //TODO:: Use mongo to fetch last synced commit for given repo
        return "";
    }

    private void updateLastSyncedCommit(String repoUrl) {
        //TODO:: Use mongo to update last synced commit for given repo
        //mongoTemplate.
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
