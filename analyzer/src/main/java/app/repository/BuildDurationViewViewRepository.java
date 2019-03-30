package app.repository;

import app.entity.BuildDurationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildDurationViewViewRepository extends JpaRepository<BuildDurationView, String> {
}
