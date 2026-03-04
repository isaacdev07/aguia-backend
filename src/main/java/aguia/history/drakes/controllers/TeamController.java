package aguia.history.drakes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.domain.Team;
import aguia.history.drakes.dtos.TeamCreationDTO;
import aguia.history.drakes.dtos.TeamDTO;
import aguia.history.drakes.services.TeamService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    // criar time
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody TeamCreationDTO dto) {
        Team createdTeam = teamService.createTeam(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }
    // deletar time com id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        // retorna resposta sem conteúdo (padrao de delete com sucesso)
        return ResponseEntity.noContent().build();
    }
}
