package app.repository;

import app.entity.GitDetailsView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GitDetailsViewRepository extends MongoRepository<GitDetailsView, String> {
    GitDetailsView findByRepoAndDate(String repo, Date date);
}
