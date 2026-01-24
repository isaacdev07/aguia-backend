package aguia.history.drakes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aguia.history.drakes.domain.MatchEvent;
import aguia.history.drakes.dtos.PlayerAssistDTO;
import aguia.history.drakes.dtos.PlayerStatsDTO;

public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
    
    @Query("SELECT new aguia.history.drakes.dtos.PlayerStatsDTO(p.name, COUNT(e)) " +
           "FROM MatchEvent e " +
           "JOIN e.player p " +
           "JOIN p.team t " +
           "WHERE t.id = :teamId " +
           "AND e.eventType = 'GOL' " +
           "GROUP BY p.name " +
           "ORDER BY COUNT(e) DESC")
    List<PlayerStatsDTO> findTopScorersByTeam(@Param("teamId") Long teamId);

    @Query("SELECT new aguia.history.drakes.dtos.PlayerAssistDTO(p.name, COUNT(e)) " +
           "FROM MatchEvent e " +
           "JOIN e.player p " +
           "JOIN p.team t " +
           "WHERE t.id = :teamId " +
           "AND e.eventType = 'ASSISTENCIA' " +
           "GROUP BY p.name " +
           "ORDER BY COUNT(e) DESC")
    List<PlayerAssistDTO> findTopAssistsByTeam(@Param("teamId") Long teamId);

}
