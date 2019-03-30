package app.repository;

import app.entity.GitCommitsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GitCommitsViewRepository extends JpaRepository<GitCommitsView, String> {
    GitCommitsView findByRepoAndDate(String repo, Date todayDate);
}
