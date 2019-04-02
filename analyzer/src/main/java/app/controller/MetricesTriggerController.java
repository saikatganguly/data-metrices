package app.controller;

import app.controller.request.MetricesTriggerSyncRequest;
import app.repository.SyncJobRepository;
import app.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static java.util.Objects.nonNull;

@RestController
@RequestMapping("/metrices/trigger")
public class MetricesTriggerController {

    private SyncJobRepository repository;
    private SyncService service;

    @Autowired
    public MetricesTriggerController(SyncJobRepository repository, SyncService service) {
        this.repository = repository;
        this.service = service;
    }

    @RequestMapping(path = "/sync", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void createStudent(@RequestBody MetricesTriggerSyncRequest request) {
        service.syncData(request);

    }

}
