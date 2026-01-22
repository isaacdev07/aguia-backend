package aguia.history.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aguia.history.drakes.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
    
}
