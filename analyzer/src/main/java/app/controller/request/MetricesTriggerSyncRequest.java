package app.controller.request;

import java.util.Date;

public class MetricesTriggerSyncRequest {
    public Date startDate;

    public MetricesTriggerSyncRequest() {
    }

    public MetricesTriggerSyncRequest(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
