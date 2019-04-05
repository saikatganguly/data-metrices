package app.controller;

import app.controller.request.MetricesTriggerSyncRequest;
import app.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metrices/trigger")
public class MetricesTriggerController {

    private SyncService service;

    @Autowired
    public MetricesTriggerController(SyncService service) {
        this.service = service;
    }

    @RequestMapping(path = "/sync", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void createStudent(@RequestBody MetricesTriggerSyncRequest request) {
        service.syncData(request);

    }

}
