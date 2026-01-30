package aguia.history.drakes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import aguia.history.drakes.domain.Player;
import aguia.history.drakes.domain.Team;
import aguia.history.drakes.domain.User;
import aguia.history.drakes.domain.enums.Position;
import aguia.history.drakes.dtos.PlayerCreateDTO;
import aguia.history.drakes.dtos.PlayerUpdateDTO;
import aguia.history.drakes.repositories.PlayerRepository;
import aguia.history.drakes.repositories.TeamRepository;

@Service
public class PlayerService {
    
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;
    //adicionar jogador a um time
    public Player createPlayer(PlayerCreateDTO dto) {
        // busca o time 
        Team team = teamRepository.findById(dto.teamId())
                .orElseThrow(() -> new RuntimeException("Time não encontrado com ID: " + dto.teamId()));

        // usa o método auxiliar para validar dono do time
        validarDono(team);

        // cria o jogador
        Player player = new Player();
        player.setName(dto.name());
        // player.setPosition(dto.position());
        player.setShirtNumber(dto.shirtNumber());
        player.setTeam(team); 
        player.setIsActive(true); 

        // facilitanco para o front poder enviar a posição como string
        try {
            // garante que a posição é válida
            player.setPosition(Position.valueOf(dto.position().toUpperCase())); 
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Posição inválida: " + dto.position());
        }

        return playerRepository.save(player);
    }

    // metodo para atualizar jogador
    public Player updatePlayer(Long playerId, PlayerUpdateDTO dto) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado."));
        // valida se o usuário logado é o dono do time do jogador
        validarDono(player.getTeam());

        // atualiza os dados do jogador
        player.setName(dto.name());
        player.setShirtNumber(dto.shirtNumber());
        
        // try catch para validar a posição
        try {
            player.setPosition(Position.valueOf(dto.position().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Posição inválida: " + dto.position());
        }

        return playerRepository.save(player);
    }

    // metodo para deletar jogador (soft delete)
    public void deletePlayer(Long playerId) {
        // procura o jogador
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado."));

        // valida se o usuário logado é o dono do time do jogador
        validarDono(player.getTeam());

        // apenas muda o status para inativo
        player.setIsActive(false); 
        
        // salva a mudança
        playerRepository.save(player);
    }

    // lista somente os jogadores ativos de certo time
    public List<Player> findActivePlayersByTeam(Long teamId) {
        return playerRepository.findByTeamIdAndIsActiveTrue(teamId);
    }

    //listar jogadores por time
    public List<Player> lisPlayersByTeam(Long teamId){

        return playerRepository.findByTeamId(teamId);
    }


    // metodo auxiliar para validar dono do time
    private void validarDono(Team team) {
        String emailLogado = getEmailLogado();
        
        // Verifica se o time tem dono e se o email bate
        if (team.getOwner() == null || !team.getOwner().getEmail().equals(emailLogado)) {
            throw new RuntimeException("ACESSO NEGADO: Você não é o dono deste time.");
        }
    }

    // metodo auxiliar para pegar email do usuário logado
    private String getEmailLogado() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

}
