package app.service;

import app.controller.request.MetricesTriggerSyncRequest;
import app.entity.SyncJob;
import app.model.BuildDetailsModel;
import app.repository.BuildDetailsModelRepository;
import app.repository.SyncJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
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
        Long timeStamp = nonNull(request) && nonNull(request.getStartDate()) ? request.getStartDate().getTime() : getStartTimestamp();
        timeStamp = processBuildDetailsModel(timeStamp);
        updateNextStartDate(timeStamp);
    }

    private Long processBuildDetailsModel(Long timeStamp) {
        List<BuildDetailsModel> buildDetailsModels = buildDetailsModelRepository.findByTimestampGreaterThanEqual(timeStamp);
        for (BuildDetailsModel buildDetailsModel : buildDetailsModels) {
            buildDetailsService.updateGitDetails(buildDetailsModel);
            if (buildDetailsModel.getTimestamp() > timeStamp) {
                timeStamp = buildDetailsModel.getTimestamp();
            }
        }
        return timeStamp;
    }

    private void updateNextStartDate(Long timestamp) {
        SyncJob syncJob = syncJobRepository.findAll().get(0);
        syncJob.setTimestamp(timestamp);
        syncJobRepository.save(syncJob);
    }

    private Long getStartTimestamp() {
        Optional<SyncJob> syncJob = syncJobRepository.findById("BuildDetailsModel");
        if (!syncJob.isPresent()) {
            SyncJob job = new SyncJob("BuildDetailsModel", new Date(1999, 01, 01, 00, 00, 00).getTime());
            syncJobRepository.save(job);
            return job.getTimestamp();
        }
        return syncJob.get().getTimestamp();
    }
}
