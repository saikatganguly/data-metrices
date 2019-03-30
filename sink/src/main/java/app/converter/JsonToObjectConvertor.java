package app.converter;

import app.model.BuildDetailsModel;
import app.model.CauseDetails;
import app.model.GitDetails;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonToObjectConvertor {

    public static Map<String, List<BuildDetailsModel>> buildJenkinsDetails(String jenkinsJsonString) throws IOException {
        Map<String, List<BuildDetailsModel>> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.readValue(jenkinsJsonString, ObjectNode.class);
        if (rootNode.has("jobs")) {
            ArrayNode jobsNode = (ArrayNode) rootNode.get("jobs");
            jobsNode.iterator().forEachRemaining(job -> {
                ArrayNode buildsNode = (ArrayNode) job.get("builds");
                List<BuildDetailsModel> li = new ArrayList<>();
                GitDetails gitDetails = new GitDetails();
                CauseDetails causeDetails = new CauseDetails();
                buildsNode.iterator().forEachRemaining(build -> {

                    ArrayNode actionsNode = (ArrayNode) build.get("actions");
                    actionsNode.iterator().forEachRemaining(action -> {
                        if (action.get("_class") != null && action.get("_class").asText().contains("CauseAction")) {
                            JsonNode cause = ((ArrayNode) action.get("causes")).iterator().next();
                            causeDetails.setCauseName(cause.get("_class") != null ? cause.get("_class").asText() : "");
                            causeDetails.setUserId(cause.get("userId") != null ? cause.get("userId").asText() : "");
                            causeDetails.setUserName(cause.get("userName") != null ? cause.get("userName").asText() : "");
                        } else if (action.get("_class") != null && action.get("_class").asText().contains("BuildData")) {
                            JsonNode lastBuildRevision = action.get("lastBuiltRevision");
                            lastBuildRevision.get("branch").iterator().forEachRemaining(branch -> {
                                gitDetails.setBranch(branch.get("name") != null ? branch.get("name").asText() : "");
                                gitDetails.setCommit(branch.get("SHA1") != null ? branch.get("SHA1").asText() : "");
                            });
                            System.out.println(action.findValue("remoteUrls"));
                            System.out.println(action.findValue("remoteUrls"));
                            /*action.get("remoteUrls").iterator().forEachRemaining(repo->{
                                gitDetails.setRepo(repo != null ? repo.asText() : "");
                            });*/
                        }

                        li.add(new BuildDetailsModel(build.get("building") != null ? build.get("building").asBoolean() : false,
                                build.get("description") != null ? build.get("description").asText() : "",
                                build.get("displayName") != null ? build.get("displayName").asText() : "",
                                build.get("duration") != null ? build.get("duration").asLong() : 0L,
                                build.get("estimatedDuration") != null ? build.get("estimatedDuration").asLong() : 0L,
                                build.get("executor") != null ? build.get("executor").asText() : "",
                                build.get("fullDisplayName") != null ? build.get("fullDisplayName").asText() : "",
                                build.get("id") != null ? build.get("id").asText() : "",
                                build.get("keepLog") != null ? build.get("description").asBoolean() : false,
                                build.get("number") != null ? build.get("number").asInt() : 0,
                                build.get("queueId") != null ? build.get("queueId").asText() : "",
                                build.get("result") != null ? build.get("result").asText() : "",
                                build.get("timestamp") != null ? build.get("timestamp").asLong() : 0L,
                                build.get("url") != null ? build.get("url").asText() : "", gitDetails, causeDetails)
                        );
                    });
                });
                map.put(job.get("name").asText(), li);
            });

            System.out.println(map);
        }
        return map;
    }
}
