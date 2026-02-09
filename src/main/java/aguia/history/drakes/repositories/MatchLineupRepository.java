package aguia.history.drakes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import aguia.history.drakes.domain.MatchLineup;
import aguia.history.drakes.domain.enums.LineupStatus;

public interface MatchLineupRepository extends JpaRepository<MatchLineup, Long> {
    // Contar quantas vezes um jogador participou como titular, reserva ou não participou
    Long countByPlayerIdAndStatus(Long playerId, LineupStatus status);

}
