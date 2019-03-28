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


    //private static String testString= "{\"_class\":\"hudson.model.Hudson\",\"jobs\":[{\"_class\":\"org.jenkinsci.plugins.workflow.job.WorkflowJob\",\"name\":\"test-pipeline\",\"url\":\"http://localhost:8080/job/test-pipeline/\",\"builds\":[{\"_class\":\"org.jenkinsci.plugins.workflow.job.WorkflowRun\",\"actions\":[{\"_class\":\"hudson.model.CauseAction\",\"causes\":[{\"_class\":\"hudson.model.Cause$UserIdCause\",\"shortDescription\":\"Started by user saikat\",\"userId\":\"admin\",\"userName\":\"saikat\"}]},{},{\"_class\":\"hudson.plugins.git.util.BuildData\",\"buildsByBranchName\":{\"refs/remotes/origin/master\":{\"_class\":\"hudson.plugins.git.util.Build\"}},\"lastBuiltRevision\":{\"SHA1\":\"7b64fc4ac386dd9e34df63feef99f2260ec9a6b0\",\"branch\":[{\"SHA1\":\"7b64fc4ac386dd9e34df63feef99f2260ec9a6b0\",\"name\":\"refs/remotes/origin/master\"}]},\"remoteUrls\":[\"https://github.com/jglick/simple-maven-project-with-tests.git\"],\"scmName\":\"\"},{\"_class\":\"hudson.plugins.git.GitTagAction\",\"tags\":[]},{},{},{},{},{\"_class\":\"org.jenkinsci.plugins.pipeline.modeldefinition.actions.RestartDeclarativePipelineAction\",\"restartEnabled\":false,\"restartableStages\":[]},{},{\"_class\":\"org.jenkinsci.plugins.workflow.job.views.FlowGraphAction\",\"nodes\":[{\"_class\":\"org.jenkinsci.plugins.workflow.graph.FlowStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepAtomNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepAtomNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.graph.FlowEndNode\"}]},{},{},{}],\"artifacts\":[],\"building\":false,\"description\":null,\"displayName\":\"#2\",\"duration\":6167,\"estimatedDuration\":8994,\"executor\":null,\"fingerprint\":[],\"fullDisplayName\":\"test-pipeline #2\",\"id\":\"2\",\"keepLog\":false,\"number\":2,\"queueId\":1,\"result\":\"FAILURE\",\"timestamp\":1553587767889,\"url\":\"http://localhost:8080/job/test-pipeline/2/\",\"changeSets\":[],\"culprits\":[],\"nextBuild\":null,\"previousBuild\":{}},{\"_class\":\"org.jenkinsci.plugins.workflow.job.WorkflowRun\",\"actions\":[{\"_class\":\"hudson.model.CauseAction\",\"causes\":[{\"_class\":\"hudson.model.Cause$UserIdCause\",\"shortDescription\":\"Started by user saikat\",\"userId\":\"admin\",\"userName\":\"saikat\"}]},{},{\"_class\":\"hudson.plugins.git.util.BuildData\",\"buildsByBranchName\":{\"refs/remotes/origin/master\":{\"_class\":\"hudson.plugins.git.util.Build\"}},\"lastBuiltRevision\":{\"SHA1\":\"7b64fc4ac386dd9e34df63feef99f2260ec9a6b0\",\"branch\":[{\"SHA1\":\"7b64fc4ac386dd9e34df63feef99f2260ec9a6b0\",\"name\":\"refs/remotes/origin/master\"}]},\"remoteUrls\":[\"https://github.com/jglick/simple-maven-project-with-tests.git\"],\"scmName\":\"\"},{\"_class\":\"hudson.plugins.git.GitTagAction\",\"tags\":[]},{},{},{},{},{\"_class\":\"org.jenkinsci.plugins.pipeline.modeldefinition.actions.RestartDeclarativePipelineAction\",\"restartEnabled\":false,\"restartableStages\":[]},{},{\"_class\":\"org.jenkinsci.plugins.workflow.job.views.FlowGraphAction\",\"nodes\":[{\"_class\":\"org.jenkinsci.plugins.workflow.graph.FlowStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepStartNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepAtomNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepAtomNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.cps.nodes.StepEndNode\"},{\"_class\":\"org.jenkinsci.plugins.workflow.graph.FlowEndNode\"}]},{},{},{}],\"artifacts\":[],\"building\":false,\"description\":null,\"displayName\":\"#1\",\"duration\":11821,\"estimatedDuration\":8994,\"executor\":null,\"fingerprint\":[],\"fullDisplayName\":\"test-pipeline #1\",\"id\":\"1\",\"keepLog\":false,\"number\":1,\"queueId\":1,\"result\":\"FAILURE\",\"timestamp\":1552748952932,\"url\":\"http://localhost:8080/job/test-pipeline/1/\",\"changeSets\":[],\"culprits\":[],\"nextBuild\":{},\"previousBuild\":null}]}]}";


    public static Map<String, List<BuildDetailsModel>> buildJenkinsDetails (String jenkinsJsonString) throws IOException {
        Map<String, List<BuildDetailsModel>> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.readValue(jenkinsJsonString , ObjectNode.class);
        if(rootNode.has("jobs")){
            ArrayNode jobsNode = (ArrayNode) rootNode.get("jobs");
            jobsNode.iterator().forEachRemaining(job->{
                ArrayNode buildsNode = (ArrayNode) job.get("builds");
                List<BuildDetailsModel> li = new ArrayList<>();
                GitDetails gitDetails=new GitDetails();
                CauseDetails causeDetails=new CauseDetails();
                buildsNode.iterator().forEachRemaining(build->{

                    ArrayNode actionsNode = (ArrayNode) build.get("actions");
                    actionsNode.iterator().forEachRemaining(action->{
                        if(action.get("_class")!=null && action.get("_class").asText().contains("CauseAction")){
                            JsonNode cause=((ArrayNode)action.get("causes")).iterator().next();
                            causeDetails.setCauseName(cause.get("_class")!=null ? cause.get("_class").asText(): "");
                            causeDetails.setUserId(cause.get("userId")!=null ? cause.get("userId").asText(): "");
                            causeDetails.setUserName(cause.get("userName")!=null ? cause.get("userName").asText(): "");
                        }
                        else if(action.get("_class")!=null && action.get("_class").asText().contains("BuildData")){
                            JsonNode lastBuildRevision = action.get("lastBuiltRevision");
                            lastBuildRevision.get("branch").iterator().forEachRemaining(branch-> {
                                gitDetails.setBranch(branch.get("name") != null ? branch.get("name").asText() : "");
                                gitDetails.setCommit(branch.get("SHA1") != null ? branch.get("SHA1").asText() : "");
                            });
                            System.out.println(action.findValue("remoteUrls"));
                            /*action.get("remoteUrls").iterator().forEachRemaining(repo->{
                                gitDetails.setRepo(repo != null ? repo.asText() : "");
                            });*/
                        }

                    li.add(new BuildDetailsModel(build.get("building")!=null?build.get("building").asBoolean():false ,
                            build.get("description")!=null?build.get("description").asText(): "",
                            build.get("displayName")!=null?build.get("displayName").asText(): "",
                            build.get("duration")!=null?build.get("duration").asLong(): 0L,
                            build.get("estimatedDuration")!=null?build.get("estimatedDuration").asLong(): 0L,
                            build.get("executor")!=null?build.get("executor").asText(): "",
                            build.get("fullDisplayName")!=null?build.get("fullDisplayName").asText(): "",
                            build.get("id")!=null?build.get("id").asText(): "",
                            build.get("keepLog")!=null?build.get("description").asBoolean(): false,
                            build.get("number")!=null?build.get("number").asInt(): 0,
                            build.get("queueId")!=null?build.get("queueId").asText(): "",
                            build.get("result")!=null?build.get("result").asText(): "",
                            build.get("timestamp")!=null?build.get("timestamp").asLong(): 0L,
                            build.get("url")!=null?build.get("url").asText(): "", gitDetails, causeDetails)
                            );


                    });
                });
                map.put(job.get("name").asText() , li);
            });

            System.out.println(map);
        }
        return map;
    }
}
