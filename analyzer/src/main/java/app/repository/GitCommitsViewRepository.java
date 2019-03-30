package app.repository;

import app.entity.GitCommitsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitCommitsViewRepository extends JpaRepository<GitCommitsView, String> {
}
