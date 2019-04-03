package app.service;

import app.model.BuildDetailsModel;
import app.processor.BuildProcessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

@Component
public class BuildDetailsService {

    private static final Logger LOGGER = Logger.getLogger(BuildDetailsService.class.getSimpleName());
    private List<BuildProcessors> processors;

    @Autowired
    public BuildDetailsService(List<BuildProcessors> processors) {
        this.processors = processors;
    }

    public void updateBuildDetails(BuildDetailsModel model) {
        try {
            process(model);
        } catch (Exception ex) {
            LOGGER.log(SEVERE, "Exception Occured for " + model + " Exception: " + ex);
        }
    }

    private void process(BuildDetailsModel model) {
        for (BuildProcessors processor : processors) {
            processor.process(model);
        }
    }
}
