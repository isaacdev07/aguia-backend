package aguia.history.drakes.repositories;

import java.util.List;
import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import aguia.history.drakes.dtos.TopCleanSheetDTO;
import aguia.history.drakes.domain.Match;

public interface MatchRepository extends JpaRepository<Match, Long> {

    // traz todas as partidas de um time específico
    List<Match> findBySeasonTeamId(Long teamId);
    

    // listar todas as partidas ativas
    List<Match> findByIsActiveTrue();

    // buscar partida ativa por id
    Optional<Match> findByIdAndIsActiveTrue(Long id);

    // listar partidas ativas de um time específico
    List<Match> findBySeasonTeamIdAndIsActiveTrue(Long teamId);

    @Query("SELECT new aguia.history.drakes.dtos.TopCleanSheetDTO(p.name, COUNT(m)) " +
       "FROM MatchLineup ml " +
       "JOIN ml.match m " +
       "JOIN ml.player p " +
       "WHERE p.team.id = :teamId " +
       "AND p.position = 'GOLEIRO' " + 
       "AND ml.status = 'TITULAR' " +
       "AND m.goalsAgainst = 0 " +      
       "GROUP BY p.name " +
       "ORDER BY COUNT(m) DESC")
List<TopCleanSheetDTO> findTopCleanSheets(@Param("teamId") Long teamId);

}