package aguia.history.drakes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import aguia.history.drakes.domain.MatchEvent;
import aguia.history.drakes.dtos.TopAssistDTO;
import aguia.history.drakes.dtos.TopCardDTO;
import aguia.history.drakes.dtos.TopScorerDTO;

public interface MatchEventRepository extends JpaRepository<MatchEvent, Long> {
    
       // encontra os principais artilheiros de um time
    @Query("SELECT new aguia.history.drakes.dtos.TopScorerDTO(p.name, COUNT(e)) " +
           "FROM MatchEvent e " +
           "JOIN e.player p " +
           "JOIN p.team t " +
           "WHERE t.id = :teamId " +
           "AND e.eventType = 'GOL' " +
           "GROUP BY p.name " +
           "ORDER BY COUNT(e) DESC")
    List<TopScorerDTO> findTopScorersByTeam(@Param("teamId") Long teamId);

       // encontra os principais assistentes de um time
    @Query("SELECT new aguia.history.drakes.dtos.TopAssistDTO(p.name, COUNT(e)) " +
           "FROM MatchEvent e " +
           "JOIN e.player p " +
           "JOIN p.team t " +
           "WHERE t.id = :teamId " +
           "AND e.eventType = 'ASSISTENCIA' " +
           "GROUP BY p.name " +
           "ORDER BY COUNT(e) DESC")
    List<TopAssistDTO> findTopAssistsByTeam(@Param("teamId") Long teamId);

       // encontra os jogadores com mais cartões de um time
    @Query("SELECT new aguia.history.drakes.dtos.TopCardDTO( " +
           "p.name, " +
           "SUM(CASE WHEN e.eventType = 'YELLOW_CARD' THEN 1L ELSE 0L END), " + 
           "SUM(CASE WHEN e.eventType = 'RED_CARD' THEN 1L ELSE 0L END), " +    
           "COUNT(e) " + 
           ") " +
           "FROM MatchEvent e " +
           "JOIN e.player p " +
           "WHERE p.team.id = :teamId " +
           "AND (e.eventType = 'YELLOW_CARD' OR e.eventType = 'RED_CARD') " + 
           "GROUP BY p.name " +
           "ORDER BY COUNT(e) DESC, p.name ASC")        
    List<TopCardDTO> findTopCardsByTeam(@Param("teamId") Long teamId);

}
