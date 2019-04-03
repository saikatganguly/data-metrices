package app.repository;

import app.entity.BuildDurationView;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BuildDurationViewRepository extends MongoRepository<BuildDurationView, String> {
    List<BuildDurationView> findByRepoAndDateBetween(String repo, Date from, Date to);
}
