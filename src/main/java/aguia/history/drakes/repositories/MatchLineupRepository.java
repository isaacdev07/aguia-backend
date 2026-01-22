package aguia.history.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aguia.history.drakes.domain.MatchLineup;

public interface MatchLineupRepository extends JpaRepository<MatchLineup, Long> {
    
}
