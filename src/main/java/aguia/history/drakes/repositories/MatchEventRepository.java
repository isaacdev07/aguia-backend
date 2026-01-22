package aguia.history.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aguia.history.drakes.domain.MatchEvent;

public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
    
}
