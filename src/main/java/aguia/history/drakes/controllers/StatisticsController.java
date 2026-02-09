package aguia.history.drakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.dtos.TopAssistDTO;
import aguia.history.drakes.dtos.TopScorerDTO;
import aguia.history.drakes.dtos.PlayerParticipationDTO;
import aguia.history.drakes.dtos.TeamStatsDTO;
import aguia.history.drakes.services.PlayerStatsService;
import aguia.history.drakes.services.StatisticsService;
// controlador para estatísticas
@RestController
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    private PlayerStatsService playerStatsService;

    @Autowired
    private StatisticsService statisticsService;
    // obter os principais artilheiros de uma equipe
    @GetMapping("/top-scorers/{teamId}")
    public ResponseEntity<List<TopScorerDTO>> getTopScorers(@PathVariable Long teamId) {
        return ResponseEntity.ok(statisticsService.getTopScorers(teamId));
    }

    // obter os principais assistentes de uma equipe
    @GetMapping("/top-assisters/{teamId}")
    public ResponseEntity<List<TopAssistDTO>> getTopAssisters(@PathVariable Long teamId) {
        return ResponseEntity.ok(statisticsService.getTopAssisters(teamId));
    }

    // obter estatísticas gerais da equipe
    @GetMapping("/team-summary/{teamId}")
    public ResponseEntity<TeamStatsDTO> getTeamSummary(@PathVariable Long teamId) {
        return ResponseEntity.ok(statisticsService.getTeamStatistics(teamId));
    }

    @GetMapping("/player-games/{playerId}")
    public ResponseEntity<PlayerParticipationDTO> getPlayerParticipation(@PathVariable Long playerId) {
        return ResponseEntity.ok(playerStatsService.getPlayerParticipationStats(playerId));
    }

}
