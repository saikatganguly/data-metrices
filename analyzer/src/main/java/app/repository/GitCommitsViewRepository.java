package app.repository;

import app.entity.GitCommitsView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GitCommitsViewRepository extends MongoRepository<GitCommitsView, String> {
    GitCommitsView findByRepoAndDate(String repo, Date todayDate);

    List<GitCommitsView> findByRepoAndDateBetween(String repo, Date from, Date to);
}
