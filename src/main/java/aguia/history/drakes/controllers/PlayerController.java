package aguia.history.drakes.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.domain.Player;
import aguia.history.drakes.services.PlayerService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // adicionar jogador a um time
    @PostMapping("/team/{teamId}")
    public ResponseEntity<Player> addPlayer(@PathVariable Long teamId, @RequestBody Player player){ 
        Player newPlayer = playerService.addPlayerToTeam(teamId, player);
        return ResponseEntity.ok(newPlayer);
    }

    // listar jogadores por time
    @GetMapping("/team/{teamId}")
        public ResponseEntity<List<Player>> listPlayersByTeam(@PathVariable Long teamId){
            return ResponseEntity.ok(playerService.lisPlayersByTeam(teamId));
        }

 }

    


