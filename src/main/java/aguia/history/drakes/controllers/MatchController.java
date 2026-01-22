package aguia.history.drakes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    
}
