package aguia.history.drakes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Player;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.repositories.PlayerRepository;
import aguia.history.drakes.repositories.TeamRepository;

@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;
    //adicionar jogador a um time
    public Player addPlayerToTeam(Long teamId, Player player) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Time nao encontrado com id: " + teamId));

        player.setTeam(team);
        return playerRepository.save(player);
    }

    //listar jogadores por time
    public List<Player> lisPlayersByTeam(Long teamId){

        return playerRepository.findByTeamId(teamId);
    }

}
