package app.controller.request;

import java.util.Date;

public class MetricesTriggerSyncRequest {
    public String buildId;

    public MetricesTriggerSyncRequest() {
    }

    public MetricesTriggerSyncRequest(String buildId) {
        this.buildId = buildId;
    }

    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }
}
