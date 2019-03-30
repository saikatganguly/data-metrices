package app.service;

import app.model.BuildDetailsModel;
import app.model.GitDetails;
import app.processor.BuildProcessors;
import app.processor.GitProcessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

@Component
public class BuildDetailsService {

    private List<BuildProcessors> processors;
    private static final Logger LOGGER = Logger.getLogger(BuildDetailsService.class.getSimpleName());

    @Autowired
    public BuildDetailsService(List<BuildProcessors> processors) {
        this.processors = processors;
    }

    @Async
    public void updateGitDetails(BuildDetailsModel model) {
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
