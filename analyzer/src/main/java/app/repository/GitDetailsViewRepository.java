package app.repository;

import app.entity.GitDetailsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GitDetailsViewRepository extends JpaRepository<GitDetailsView, String> {
}
