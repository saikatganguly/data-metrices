package app.processor;

import app.entity.BuildDurationView;
import app.model.BuildDetailsModel;
import app.repository.BuildDurationViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.util.Objects.isNull;
import static java.util.UUID.randomUUID;

@Component
public class BuildDurationProcessor implements BuildProcessors {

    private BuildDurationViewRepository repository;

    @Autowired
    public BuildDurationProcessor(BuildDurationViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public void process(BuildDetailsModel details) {
        Date todayDate = new Date(details.getTimestamp());
        if (isNull(repository.findByRepoAndDate(details.getGitDetails().getRepo(), todayDate))) {
            BuildDurationView view = new BuildDurationView(randomUUID().toString(), details.getGitDetails().getRepo(), details.getGitDetails().getBranch(), details.getDuration(), todayDate);
            repository.save(view);
        }
    }
}
