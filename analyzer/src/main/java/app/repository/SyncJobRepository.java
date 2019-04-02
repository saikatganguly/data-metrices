package app.repository;

import app.entity.SyncJob;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SyncJobRepository extends MongoRepository<SyncJob, String> {
}
