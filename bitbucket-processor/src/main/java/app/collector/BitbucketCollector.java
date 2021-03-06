package app.collector;

import app.model.BitbucketRepo;
import app.model.CommitInfo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.valueOf;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class BitbucketCollector {

    private static final Logger LOG = LoggerFactory.getLogger(BitbucketCollector.class);

    private static final String USERID = "asanodia";
    private static final String PASSWORD = "asanodia";
    private static String BASE_PATH = "http://192.168.99.100:7990";
    private static String PROJECT_NAME = "TEST-PROJECT-KEY-1";

    private RestTemplate bitbucketRestTemplate;

    @Autowired
    public BitbucketCollector(@Qualifier("bitbucketRestTemplate") RestTemplate bitbucketRestTemplate) {
        this.bitbucketRestTemplate = bitbucketRestTemplate;
    }

    public List<Map<String, CommitInfo>> collect() throws MalformedURLException {
        BitbucketRepo bitbucketRepo = new BitbucketRepo(BASE_PATH, PROJECT_NAME);

        ResponseEntity<String> repos = bitbucketRestTemplate.exchange(bitbucketRepo.getAllRepoUrl(), HttpMethod.GET,
                new HttpEntity<>(createHeaders(USERID, PASSWORD)),
                String.class);
        JSONObject reposParentObject = paresAsObject(repos);
        JSONArray reposArray = (JSONArray) reposParentObject.get("values");

        List<Map<String, CommitInfo>> reposInformation = new ArrayList<>();
        for (Object repo : reposArray) {
            JSONObject repoJsonObject = (JSONObject) repo;
            String repoName = str(repoJsonObject, "name");
            bitbucketRepo.setRepoName(repoName);

            reposInformation.add(repoInformation(bitbucketRepo));
        }

        return reposInformation;
    }

    public Map<String, CommitInfo> repoInformation(BitbucketRepo bitbucketRepo) throws MalformedURLException {
        return repoInformation(bitbucketRepo, null);
    }

    public Map<String, CommitInfo> repoInformation(BitbucketRepo bitbucketRepo, String since) throws MalformedURLException {
        return repoInformation(bitbucketRepo, since, null);
    }

    public Map<String, CommitInfo> repoInformation(BitbucketRepo bitbucketRepo, String since, String until) throws MalformedURLException {

        //Fetching commits
        String commitsUrl = bitbucketRepo.getRepoUrl() + "/commits?";

        if (!isEmpty(since)) {
            commitsUrl = commitsUrl + "since=" + since;
        }
        if (!isEmpty(until)) {
            commitsUrl = commitsUrl + "&until=" + until;
        }

        Map<String, CommitInfo> commitInformation = commits(commitsUrl);

        //Fetching tags
        String tagUrl = bitbucketRepo.getRepoUrl() + "/tags";
        Map<String, CommitInfo> tagsInformation = tags(tagUrl);

        Map<String, CommitInfo> repoInformation = merge(commitInformation, tagsInformation);

        repoInformation
                .entrySet()
                .stream()
                .forEach(entry -> System.out.println(entry.getValue()));

        return repoInformation;
    }

    public Map<String, CommitInfo> tags(String tagUrl) {
        ResponseEntity<String> tags = null;
        try {
            tags = bitbucketRestTemplate.exchange(tagUrl, HttpMethod.GET,
                    new HttpEntity<>(createHeaders(USERID, PASSWORD)),
                    String.class);
        } catch (Exception exception) {
            LOG.error("Unable to fetch tags for repo {}, exception {}", tagUrl, exception.getMessage());
            return new HashMap<>();
        }

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

            tagsInformation.put(commitId, commitInfo);
        }

        return tagsInformation;
    }

    public Map<String, CommitInfo> commits(String commitsUrl) {
        ResponseEntity<String> commits = null;
        try {
            commits = bitbucketRestTemplate.exchange(commitsUrl, HttpMethod.GET,
                    new HttpEntity<>(createHeaders(USERID, PASSWORD)),
                    String.class);
        } catch (Exception exception) {
            LOG.error("Unable to fetch commits for repo {}, exception {}", commitsUrl, exception.getMessage());
            return new HashMap<>();
        }

        JSONObject commitsParentObject = paresAsObject(commits);
        JSONArray commitsArray = (JSONArray) commitsParentObject.get("values");

        Map<String, CommitInfo> commitInformation = new HashMap<>();

        for (Object commit : commitsArray) {
            CommitInfo commitInfo = new CommitInfo();

            JSONObject commitJsonObject = (JSONObject) commit;
            String commitId = str(commitJsonObject, "id");
            String commitMessage = str(commitJsonObject, "message");
            String committerTimestamp = str(commitJsonObject, "committerTimestamp");

            JSONObject authorObject = (JSONObject) commitJsonObject.get("author");
            String authorName = str(authorObject, "name");
            String authorEmailAddress = str(authorObject, "emailAddress");

            commitInfo.setId(commitId);
            commitInfo.setAuthorName(authorName);
            commitInfo.setAuthorEmailAddress(authorEmailAddress);
            commitInfo.setMessage(commitMessage);
            commitInfo.setTimeStamp(valueOf(committerTimestamp));

            commitInformation.put(commitId, commitInfo);
        }

        return commitInformation;
    }

    private Map<String, CommitInfo> merge(Map<String, CommitInfo> commitInformation, Map<String, CommitInfo> tagsInformation) {
        Map<String, CommitInfo> repoInformation = new HashMap<>();
        commitInformation
                .entrySet()
                .stream()
                .forEach(commit -> {
                            String commitId = commit.getValue().getId();
                            CommitInfo commitInfo = commit.getValue();
                            CommitInfo tagsInfo = tagsInformation.get(commitId);
                            commit.getValue().setTag(tagsInfo.getTag());
                            repoInformation.put(commitId, commitInfo);
                        }
                );
        return repoInformation;
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
