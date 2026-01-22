package aguia.history.drakes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.domain.Team;
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

    @PostMapping
    public ResponseEntity<TeamDTO> create(@RequestBody TeamDTO dto) {
        // dto para entidade
        Team inputTeam = dto.toEntity();
        //salva o time
        Team savedTeam = teamService.createTeam(inputTeam);
        //  retorna o time salvo com DTO
        return ResponseEntity.ok(new TeamDTO(savedTeam));
    }
    // deletar time com id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        // retorna resposta sem conteúdo (padrao de delete com sucesso)
        return ResponseEntity.noContent().build();
    }
}
