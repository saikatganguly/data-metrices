package app.service;

import app.controller.request.MetricesTriggerSyncRequest;
import app.entity.SyncJob;
import app.model.BuildDetailsModel;
import app.repository.BuildDetailsModelRepository;
import app.repository.SyncJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@Component
public class SyncService {

    private static final Logger LOGGER = Logger.getLogger(SyncService.class.getSimpleName());
    private SyncJobRepository syncJobRepository;
    private BuildDetailsModelRepository buildDetailsModelRepository;
    private BuildDetailsService buildDetailsService;

    @Autowired
    public SyncService(SyncJobRepository syncJobRepository, BuildDetailsModelRepository buildDetailsModelRepository, BuildDetailsService buildDetailsService) {
        this.syncJobRepository = syncJobRepository;
        this.buildDetailsModelRepository = buildDetailsModelRepository;
        this.buildDetailsService = buildDetailsService;
    }

    @Async
    public void syncData(MetricesTriggerSyncRequest request) {
        processBuildDetails(request);
    }

    private void processBuildDetails(MetricesTriggerSyncRequest request) {
        String jobId = nonNull(request) && nonNull(request.getBuildId()) ? request.getBuildId() : getStartTimestamp("BuildDetailsModel");
        jobId = processBuildsFrom(jobId);
        updateNextStartDate(jobId, "BuildDetailsModel");
    }

    private String processBuildsFrom(String jobId) {
        List<BuildDetailsModel> buildDetailsModels = buildDetailsModelRepository.findAll();
        for (BuildDetailsModel buildDetailsModel : buildDetailsModels) {
            buildDetailsService.updateBuildDetails(buildDetailsModel);
            jobId = buildDetailsModel.getId();
        }
        return jobId;
    }

    private void updateNextStartDate(String jobId, String syncJobKey) {
        SyncJob syncJob = syncJobRepository.findById(syncJobKey).get();
        syncJob.setJobId(jobId);
        syncJobRepository.save(syncJob);
    }

    private String getStartTimestamp(String syncJobKey) {
        Optional<SyncJob> syncJob = syncJobRepository.findById(syncJobKey);
        if (!syncJob.isPresent()) {
            SyncJob job = new SyncJob(syncJobKey, "0");
            syncJobRepository.save(job);
            return job.getJobId();
        }
        return syncJob.get().getJobId();
    }
}
