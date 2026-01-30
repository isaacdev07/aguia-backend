package aguia.history.drakes.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aguia.history.drakes.domain.Player;
import aguia.history.drakes.dtos.PlayerCreateDTO;
import aguia.history.drakes.dtos.PlayerDTO;
import aguia.history.drakes.services.PlayerService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // adicionar um novo jogador
    @PostMapping
    public ResponseEntity<PlayerDTO> create(@RequestBody @Valid PlayerCreateDTO dto) {
        // pega o player criado no service 
        Player savedPlayer = playerService.createPlayer(dto);
        // retorna o jogacor criado com dto
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

    


