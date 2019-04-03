package app.controller.request;

import app.model.BitbucketRepo;
import app.model.BuildDetailsModel;
import app.model.CommitInfo;

import java.util.List;
import java.util.Map;

public class DataCreationRequest {
    private List<BuildDetailsModel> buildDetailsModels;
    private List<Map<BitbucketRepo, Map<String, CommitInfo>>> bitbucketRepoInfo;
    private List<Map<String, String>> sonarProjectInfo;

    public DataCreationRequest() {
    }

    public DataCreationRequest(List<BuildDetailsModel> buildDetailsModels,
                               List<Map<BitbucketRepo, Map<String, CommitInfo>>> bitbucketRepoInfo,
                               List<Map<String, String>> sonarProjectInfo) {
        this.buildDetailsModels = buildDetailsModels;
        this.bitbucketRepoInfo = bitbucketRepoInfo;
        this.sonarProjectInfo = sonarProjectInfo;
    }

    public List<BuildDetailsModel> getBuildDetailsModels() {
        return buildDetailsModels;
    }

    public void setBuildDetailsModels(List<BuildDetailsModel> buildDetailsModels) {
        this.buildDetailsModels = buildDetailsModels;
    }

    public List<Map<BitbucketRepo, Map<String, CommitInfo>>> getBitbucketRepoInfo() {
        return bitbucketRepoInfo;
    }

    public void setBitbucketRepoInfo(List<Map<BitbucketRepo, Map<String, CommitInfo>>> bitbucketRepoInfo) {
        this.bitbucketRepoInfo = bitbucketRepoInfo;
    }

    public List<Map<String, String>> getSonarProjectInfo() {
        return sonarProjectInfo;
    }

    public void setSonarProjectInfo(List<Map<String, String>> sonarProjectInfo) {
        this.sonarProjectInfo = sonarProjectInfo;
    }
}
