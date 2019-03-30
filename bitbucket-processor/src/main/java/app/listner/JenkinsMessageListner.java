package app.listener;

import app.config.SourceDestination;
import app.converter.BitbucketCollector;
import app.model.BitbucketRepo;
import app.model.BuildDetailsModel;
import app.model.CommitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class JenkinsMessageListner {

    private BitbucketCollector collector;

    @Autowired
    public JenkinsMessageListner(BitbucketCollector collector) {
        this.collector = collector;
    }

    @StreamListener(SourceDestination.INPUT)
    @SendTo(SourceDestination.OUTPUT)
    public List<Map<BitbucketRepo, Map<String, CommitInfo>>> handleMessages(@Payload Map<String, List<BuildDetailsModel>> jenkinsBuildInfo) throws MalformedURLException {

        List<Map<BitbucketRepo, Map<String, CommitInfo>>> buildsRepoInformationList = new ArrayList<>();

        for (List<BuildDetailsModel> buildDetailsModels : jenkinsBuildInfo.values()) {
            for (BuildDetailsModel buildDetailsModel : buildDetailsModels) {
                //TODO : Need to fix this model
                //Currently it is getting all builds information for
                //Need to revisit
                String repoUrl = buildDetailsModel.getGitDetails().getRepo();

                Map<BitbucketRepo, Map<String, CommitInfo>> repoInformation = getRepoInformation(repoUrl);

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

    public Map<BitbucketRepo, Map<String, CommitInfo>> getRepoInformation(String repoUrl) throws MalformedURLException {
        URL url = new URL(repoUrl);
        String hostName = url.getProtocol() + "://" + url.getAuthority();

        String substring = repoUrl.substring(0, repoUrl.indexOf(".git"));

        String[] splitRepoUrl = substring.split("/");
        int length = splitRepoUrl.length;

        String projectName = splitRepoUrl[length - 2];
        String repoName = splitRepoUrl[length - 1];

        BitbucketRepo bitbucketRepo = new BitbucketRepo(hostName, projectName, repoName);
        return collector.repoInformation(bitbucketRepo);
    }
}
