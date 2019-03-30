package app.listner;

import app.converter.BitbucketCollector;
import app.listener.JenkinsMessageListner;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

public class JenkinsMessageListnerTest {

    private JenkinsMessageListner jenkinsMessageListner;

    @Test
    public void shouldFetchRepoInformation() throws MalformedURLException {
        jenkinsMessageListner = new JenkinsMessageListner(new BitbucketCollector(new RestTemplate()));

        jenkinsMessageListner.getRepoInformation("http://192.168.99.100:7990/scm/test-project-key-1/test-repository-1.git");
    }
}