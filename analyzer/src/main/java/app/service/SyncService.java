package app.service;

import app.controller.request.MetricesTriggerSyncRequest;
import app.entity.SyncJob;
import app.repository.SyncJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

import static java.util.Objects.nonNull;

@Component
public class SyncService {

    private static final Logger LOGGER = Logger.getLogger(SyncService.class.getSimpleName());
    private SyncJobRepository repository;

    @Autowired
    public SyncService(SyncJobRepository repository) {
        this.repository = repository;
    }

    @Async
    public void syncData(MetricesTriggerSyncRequest request) {
        Date startDate = nonNull(request) && nonNull(request.getStartDate()) ? request.getStartDate() : getStartDate();

        updateNextStartDate(startDate);
    }

    private void updateNextStartDate(Date startDate) {
        SyncJob syncJob = repository.findAll().get(0);
        syncJob.setDate(startDate);
        repository.save(syncJob);
    }

    private Date getStartDate() {
        return repository.findAll().get(0).getDate();
    }
}
