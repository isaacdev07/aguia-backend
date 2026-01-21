package aguia.history.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import aguia.history.drakes.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    
}