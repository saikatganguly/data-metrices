package app.repository;

import app.entity.GitDetailsView;
import app.entity.SyncJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SyncJobRepository extends JpaRepository<SyncJob, String> {
}
