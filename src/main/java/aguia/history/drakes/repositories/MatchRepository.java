package aguia.history.drakes.repositories;

import java.util.List;
import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository;

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

}