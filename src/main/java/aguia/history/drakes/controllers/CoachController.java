package aguia.history.drakes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.domain.Coach;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.services.CoachService;  

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/coaches")
public class CoachController {

    @Autowired
    private CoachService coachService;

    //criar novo tecnico
    @PostMapping
    public ResponseEntity<Coach> create(@RequestBody Coach coach) {
        return ResponseEntity.ok(coachService.create(coach));
    }

    // listar todos os tecnicos
    @GetMapping
    public ResponseEntity<List<Coach>> findAll() {
        return ResponseEntity.ok(coachService.findAll());
    }   

    //associar tecnico a um time
    @PatchMapping("/hire/{teamId}/{coachId}")
    public ResponseEntity<Team> hire(@PathVariable Long teamId, @PathVariable Long coachId) {
        return ResponseEntity.ok(coachService.hireCoach(teamId, coachId));
    }

    //demitir tecnico de um time
    @PatchMapping("/fire/{teamId}")
    public ResponseEntity<Team> fire(@PathVariable Long teamId) {
        return ResponseEntity.ok(coachService.fireCoach(teamId));
    }   
    
    
}
