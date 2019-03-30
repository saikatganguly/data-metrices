package app.service;

import app.model.GitDetails;
import app.processor.GitProcessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.logging.Logger;
import static java.util.logging.Level.SEVERE;

@Component
public class GitDetailsService {

    private List<GitProcessors> processors;
    private static final Logger LOGGER = Logger.getLogger(GitDetailsService.class.getSimpleName());

    @Autowired
    public GitDetailsService(List<GitProcessors> processors) {
        this.processors = processors;
    }

    @Async
    public void updateGitDetails(GitDetails gitDetails) {
        try {
            process(gitDetails);
        } catch (Exception ex) {
            LOGGER.log(SEVERE, "Exception Occured for " + gitDetails + " Exception: " + ex);
        }
    }

    private void process(GitDetails gitDetails) {
        for (GitProcessors processor : processors) {
            processor.process(gitDetails);
        }
    }
}
