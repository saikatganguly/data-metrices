package app.converter;

import app.model.CommitInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Component
public class BitbucketCollector {

    private static final Log LOG = LogFactory.getLog(BitbucketCollector.class);

    private static final String USERID = "asanodia";
    private static final String PASSWORD = "asanodia";
    private static String BASE_PATH = "http://192.168.99.100:7990/rest/api/1.0/projects/";
    private static String PROJECT_NAME = "TEST-PROJECT-KEY-1";

    @Autowired
    @Qualifier("bitbucketRestTemplate")
    private RestTemplate bitbucketRestTemplate;

    public void collect() {
        String reposUrl = BASE_PATH + PROJECT_NAME + "/repos";
        ResponseEntity<String> repos = bitbucketRestTemplate.exchange(reposUrl, HttpMethod.GET,
                new HttpEntity<>(createHeaders(USERID, PASSWORD)),
                String.class);
        JSONObject reposParentObject = paresAsObject(repos);
        JSONArray reposArray = (JSONArray) reposParentObject.get("values");

        for (Object repo : reposArray) {
            JSONObject repoJsonObject = (JSONObject) repo;
            String repoName = str(repoJsonObject, "name");
            System.out.println("repoName = " + repoName);

            //Fetching commits
            String commitsUrl = reposUrl + "/" + repoName + "/commits";
            commits(commitsUrl);

            //Fetching tags
            String tagUrl = reposUrl + "/" + repoName + "/tags";
            tags(tagUrl);
        }
    }

    public Map<String, CommitInfo> tags(String tagUrl) {
        ResponseEntity<String> tags = bitbucketRestTemplate.exchange(tagUrl, HttpMethod.GET,
                new HttpEntity<>(createHeaders(USERID, PASSWORD)),
                String.class);
        JSONObject tagsParentObject = paresAsObject(tags);
        JSONArray tagsArray = (JSONArray) tagsParentObject.get("values");
        Map<String, CommitInfo> tagsInformation = new HashMap<>();
        for (Object tag : tagsArray) {
            CommitInfo commitInfo = new CommitInfo();

            JSONObject tagJsonObject = (JSONObject) tag;
            String commitId = str(tagJsonObject, "latestCommit");
            String tagValue = str(tagJsonObject, "displayId");

            commitInfo.setId(commitId);
            commitInfo.setTag(tagValue);

            System.out.println("     commitId = " + commitId);
            System.out.println("     tagValue = " + tagValue);
            tagsInformation.put(commitId, commitInfo);
        }

        return tagsInformation;
    }

    public Map<String, CommitInfo> commits(String commitsUrl) {
        ResponseEntity<String> commits = bitbucketRestTemplate.exchange(commitsUrl, HttpMethod.GET,
                new HttpEntity<>(createHeaders(USERID, PASSWORD)),
                String.class);
        JSONObject commitsParentObject = paresAsObject(commits);
        JSONArray commitsArray = (JSONArray) commitsParentObject.get("values");

        Map<String, CommitInfo> commitInformation = new HashMap<>();

        for (Object commit : commitsArray) {
            CommitInfo commitInfo = new CommitInfo();

            JSONObject commitJsonObject = (JSONObject) commit;
            String commitId = str(commitJsonObject, "id");
            String commitMessage = str(commitJsonObject, "message");

            JSONObject authorObject = (JSONObject) commitJsonObject.get("author");
            String authorName = str(authorObject, "name");
            String authorEmailAddress = str(authorObject, "emailAddress");

            commitInfo.setId(commitId);
            commitInfo.setAuthorName(authorName);
            commitInfo.setAuthorEmailAddress(authorEmailAddress);
            commitInfo.setMessage(commitMessage);

            System.out.println("     commitId = " + commitId);
            System.out.println("     authorName = " + authorName);
            System.out.println("     authorEmailAddress = " + authorEmailAddress);
            System.out.println("     commitMessage = " + commitMessage);

            commitInformation.put(commitId, commitInfo);
        }

        return commitInformation;
    }

    private HttpHeaders createHeaders(final String userId, final String password) {
        String auth = userId + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.US_ASCII));
        String authHeader = "Basic " + new String(encodedAuth);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        return headers;
    }

    private JSONObject paresAsObject(ResponseEntity<String> response) {
        try {
            return (JSONObject) new JSONParser().parse(response.getBody());
        } catch (ParseException pe) {
            LOG.error(pe.getMessage());
        }
        return new JSONObject();
    }

    private String str(JSONObject json, String key) {
        Object value = json.get(key);
        return value == null ? null : value.toString();
    }
}
