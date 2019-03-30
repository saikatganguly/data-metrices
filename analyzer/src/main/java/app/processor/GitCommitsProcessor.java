package app.processor;

import app.entity.GitCommitsView;
import app.model.GitDetails;
import app.repository.GitCommitsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;
import static java.util.Objects.nonNull;
import static java.util.UUID.randomUUID;

@Component
public class GitCommitsProcessor implements GitProcessors {

    private GitCommitsViewRepository repository;

    @Autowired
    public GitCommitsProcessor(GitCommitsViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public void process(GitDetails details) {
        Date todayDate = new Date();
        GitCommitsView view = repository.findByRepoAndDate(details.getRepo(), todayDate);
        if (nonNull(view)) {
            view.incrementCount();
        } else {
            view = new GitCommitsView(randomUUID().toString(), details.getRepo(), details.getBranch(), 1, todayDate);
        }
        repository.save(view);
    }
}
