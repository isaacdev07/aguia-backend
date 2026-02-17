package aguia.history.drakes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aguia.history.drakes.domain.MatchLineup;
import aguia.history.drakes.domain.enums.LineupStatus;
import aguia.history.drakes.dtos.PlayerParticipationDTO;

public interface MatchLineupRepository extends JpaRepository<MatchLineup, Long> {
    // contar quantas vezes um jogador participou como titular, reserva ou não participou
    Long countByPlayerIdAndStatus(Long playerId, LineupStatus status);

    // consulta personalizada para obter as estatísticas de participação dos jogadores de uma equipe
    @Query("SELECT new aguia.history.drakes.dtos.PlayerParticipationDTO( " +
           "p.name, " +
           "SUM(CASE WHEN ml.status = 'TITULAR' OR ml.status = 'RESERVA_ENTROU' THEN 1L ELSE 0L END), " + 
           "SUM(CASE WHEN ml.status = 'TITULAR' THEN 1L ELSE 0L END), " + 
           "SUM(CASE WHEN ml.status = 'RESERVA_ENTROU' THEN 1L ELSE 0L END), " + 
           "SUM(CASE WHEN ml.status = 'RESERVA_NAO_ENTROU' THEN 1L ELSE 0L END) " + 
           ") " +
           "FROM MatchLineup ml " +
           "JOIN ml.player p " +
           "WHERE p.team.id = :teamId " +
           "GROUP BY p.id, p.name " +
           "ORDER BY SUM(CASE WHEN ml.status = 'TITULAR' OR ml.status = 'RESERVA_ENTROU' THEN 1L ELSE 0L END) DESC")
    List<PlayerParticipationDTO> getStatsByTeam(@Param("teamId") Long teamId);

}
