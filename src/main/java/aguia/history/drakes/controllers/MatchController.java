package aguia.history.drakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.domain.Match;
import aguia.history.drakes.dtos.MatchCreateDTO;
import aguia.history.drakes.services.MatchService;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping
    public ResponseEntity<Match> create(@RequestBody MatchCreateDTO dto) {
        Match savedMatch = matchService.createMatch(dto);
        return ResponseEntity.ok(savedMatch);
    }
    
    // listar todas as partidas
    @GetMapping
    public ResponseEntity<List<Match>> findAll() {
        List<Match> matches = matchService.findAllMatches();
        return ResponseEntity.ok(matches);
    }

    //listar partida por id
    @GetMapping("/{id}")
    public ResponseEntity<Match> findById(@PathVariable Long id) {
        Match match = matchService.findMatchById(id);
        return ResponseEntity.ok(match);
    }

    //listar partidas por id da equipe
    @GetMapping("/team/{teamId}")
    public ResponseEntity<List<Match>> findByTeamId(@PathVariable Long teamId)  {
        List<Match> matches = matchService.findMatchesByTeam(teamId);
        return ResponseEntity.ok(matches);
    }
}
