package aguia.history.drakes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import aguia.history.drakes.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {
    // encontrar partidas por ID da equipe da temporada
    List<Match> findBySeasonTeamId(Long teamId);
    
}   
