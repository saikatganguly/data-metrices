package app.repository;

import app.entity.GitCommitsView;
import app.entity.GitDetailsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GitDetailsViewRepository extends JpaRepository<GitDetailsView, String> {
    GitDetailsView findByRepoAndDate(String repo, Date date);
}
