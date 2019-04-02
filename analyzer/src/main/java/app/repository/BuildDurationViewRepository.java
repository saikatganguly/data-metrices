package app.repository;

import app.entity.BuildDurationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BuildDurationViewRepository extends JpaRepository<BuildDurationView, String> {
    BuildDurationView findByRepoAndDate(String repo, Date todayDate);

    Long getAverageOfDurationByRepoAndDateBetween(String repo, Date from, Date to);
}
