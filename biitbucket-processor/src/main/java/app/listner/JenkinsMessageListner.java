package app.listener;

import app.config.SourceDestination;
import app.converter.BitbucketCollector;
import app.model.BuildDetailsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component
public class JenkinsMessageListner {

    @Autowired
    private BitbucketCollector collector;

    @StreamListener(SourceDestination.INPUT)
    @SendTo(SourceDestination.OUTPUT)
    public Map<String, List<BuildDetailsModel>> handleMessages(@Payload Map<String, List<BuildDetailsModel>> jenkinsBuildInfo) throws MalformedURLException {

        for (List<BuildDetailsModel> buildDetailsModels : jenkinsBuildInfo.values()) {
            for (BuildDetailsModel buildDetailsModel : buildDetailsModels) {
                //TODO : Need to fix this model
                String repoUrl = buildDetailsModel.getGitDetails().getRepo();

                repoInformation(repoUrl);
            }
        }
        return null;
    }

    private void repoInformation(String repoUrl) throws MalformedURLException {
        URL url = new URL(repoUrl);

        String substring = repoUrl.substring(0, repoUrl.indexOf(".git"));

        String[] splitRepoUrl = substring.split("/");
        int length = splitRepoUrl.length;

        String projectName = splitRepoUrl[length - 2];
        String repoName = splitRepoUrl[length - 1];

        String partialRepoUrl = url.getProtocol() + "://" + url.getAuthority() + "/rest/api/1.0/projects/" + projectName + "/repos/" + repoName;

        //Fetching commits
        String commitsUrl = partialRepoUrl + "/commits";
        collector.commits(commitsUrl);

        //Fetching tags
        String tagUrl = partialRepoUrl + "/tags";
        collector.tags(tagUrl);
    }


    //For testing purpose
    public static void main(String[] args) throws MalformedURLException {
        new JenkinsMessageListner().repoInformation("http://192.168.99.100:7990/scm/test-project-key-1/test-repository-1.git");
    }
}
