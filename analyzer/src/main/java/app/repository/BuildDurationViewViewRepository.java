package app.repository;

import app.entity.BuildDurationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface BuildDurationViewViewRepository extends JpaRepository<BuildDurationView, String> {
    BuildDurationView findByRepoAndDate(String repo, Date todayDate);
}
