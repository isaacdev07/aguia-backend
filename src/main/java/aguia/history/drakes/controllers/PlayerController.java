package aguia.history.drakes.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.domain.Player;
import aguia.history.drakes.dtos.PlayerDTO;
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
    public ResponseEntity<PlayerDTO> addPlayer(@PathVariable Long teamId, @RequestBody PlayerDTO dto){ 
        // dto para entidade
        Player inputPlayer = dto.toEntity();
        // salva o jogador
        Player savedPlayer = playerService.addPlayerToTeam(teamId, inputPlayer);
        // retorna o jogador salvo com DTO
        return ResponseEntity.ok(new PlayerDTO(savedPlayer));
    }

    // listar jogadores por time
    @GetMapping("/team/{teamId}")
        public ResponseEntity<List<PlayerDTO>> listPlayersByTeam(@PathVariable Long teamId){
        
        List<Player> list = playerService.lisPlayersByTeam(teamId);

            List<PlayerDTO> dtoList = list.stream()
                .map(PlayerDTO::new)
                .collect(Collectors.toList());

                return ResponseEntity.ok(dtoList);
        }

 }

    


