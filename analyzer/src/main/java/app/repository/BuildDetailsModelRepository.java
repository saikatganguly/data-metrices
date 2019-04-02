package app.repository;

import app.model.BuildDetailsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildDetailsModelRepository extends MongoRepository<BuildDetailsModel, String> {
    List<BuildDetailsModel> findByTimestampGreaterThanEqual(Long timestamp);
}
