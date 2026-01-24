package aguia.history.drakes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Match;
import aguia.history.drakes.dtos.PlayerAssistDTO;
import aguia.history.drakes.dtos.PlayerStatsDTO;
import aguia.history.drakes.dtos.TeamStatsDTO;
import aguia.history.drakes.repositories.MatchEventRepository;
import aguia.history.drakes.repositories.MatchRepository;

@Service
public class StatisticsService {

    @Autowired
    private MatchEventRepository matchEventRepository;

    @Autowired
    private MatchRepository matchRepository;

    // obter os principais artilheiros de uma equipe
    public List<PlayerStatsDTO> getTopScorers(Long teamId) {
        return matchEventRepository.findTopScorersByTeam(teamId);
    }
    // obter estatísticas gerais da equipe
   public TeamStatsDTO getTeamStatistics(Long teamId) {
        // busca todas as partidas da equipe na temporada
        List<Match> matches = matchRepository.findBySeasonTeamId(teamId);

        TeamStatsDTO stats = new TeamStatsDTO();
        stats.setTotalMatches(matches.size());

        // inicializa contadores
        for (Match match : matches) {
            // soma gols feitos e sofridos
            stats.setGoalsScored(stats.getGoalsScored() + match.getGoalsFor());
            stats.setGoalsConceded(stats.getGoalsConceded() + match.getGoalsAgainst());

            // verifica resultado da partida
            if (match.getGoalsFor() > match.getGoalsAgainst()) {
                stats.setWins(stats.getWins() + 1);
            } else if (match.getGoalsFor() < match.getGoalsAgainst()) {
                stats.setLosses(stats.getLosses() + 1);
            } else {
                stats.setDraws(stats.getDraws() + 1);
            }
        }

        // calcula saldo de gols
        stats.setGoalBalance(stats.getGoalsScored() - stats.getGoalsConceded());

        return stats;
    }

    // obter os principais assistentes de uma equipe
    public List<PlayerAssistDTO> getTopAssisters(Long teamId) {
        return matchEventRepository.findTopAssistsByTeam(teamId);
    }   

}



