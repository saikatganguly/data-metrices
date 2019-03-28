package app.importer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import org.apache.commons.codec.binary.Base64;
import org.springframework.batch.item.ItemReader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.Charset;

public class JenkinsDataImporter implements ItemReader<String>{

    private boolean batchJobState = false;
    private String jenkinsUrl= "http://localhost:8080/api/json?tree=jobs[name,url,builds[*,artifacts[*],changesets[*]," +
            "previousBuild,fingerprint[*],culprits[*],actions[*,tags[*],causes[shortDescription,upstreamProject,upstreamBuild,upstreamUrl," +
            "userId,userName],lastBuiltRevision[*,branch[*]]]]]&pretty=true";

    public String read() throws Exception {
        if(!batchJobState) {
            RestTemplate restTemplate = new RestTemplate();
            String result = restTemplate.getForObject(jenkinsUrl, String.class);

            System.out.println(result);
            batchJobState=true;
            return result;
        }
        batchJobState = false;
        return null;
    }
}
