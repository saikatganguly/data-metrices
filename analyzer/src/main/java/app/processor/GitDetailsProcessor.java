package app.processor;

import app.entity.GitDetailsView;
import app.model.GitDetails;
import app.repository.GitDetailsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static java.util.Objects.isNull;
import static java.util.UUID.randomUUID;

@Component
public class GitDetailsProcessor implements GitProcessors {

    private GitDetailsViewRepository repository;

    @Autowired
    public GitDetailsProcessor(GitDetailsViewRepository repository) {
        this.repository = repository;
    }

    @Override
    public void process(GitDetails details) {
        Date todayDate = new Date();
        if (isNull(repository.findByRepoAndDate(details.getRepo(), todayDate))) {
            GitDetailsView view = new GitDetailsView(randomUUID().toString(), details.getRepo(), details.getBranch(), details.getCommit(), todayDate);
            repository.save(view);
        }
    }
}
