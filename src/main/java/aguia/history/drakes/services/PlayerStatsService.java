package aguia.history.drakes.services;

import aguia.history.drakes.domain.Player;
import aguia.history.drakes.domain.enums.LineupStatus;
import aguia.history.drakes.dtos.PlayerParticipationDTO;
import aguia.history.drakes.repositories.MatchLineupRepository;
import aguia.history.drakes.repositories.PlayerRepository; 
import org.springframework.stereotype.Service;

@Service
public class PlayerStatsService {

    private final MatchLineupRepository lineupRepository;
    private final PlayerRepository playerRepository; 

    // constructor 
    public PlayerStatsService(MatchLineupRepository lineupRepository, 
                              PlayerRepository playerRepository) {
        this.lineupRepository = lineupRepository;
        this.playerRepository = playerRepository;
    }

    public PlayerParticipationDTO getPlayerParticipationStats(Long playerId) {
        
        // busca o player
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Jogador não encontrado com ID: " + playerId));

        // faz as contagens
        Long starters = lineupRepository.countByPlayerIdAndStatus(playerId, LineupStatus.TITULAR);
        Long subsIn = lineupRepository.countByPlayerIdAndStatus(playerId, LineupStatus.RESERVA_ENTROU);
        Long benchOnly = lineupRepository.countByPlayerIdAndStatus(playerId, LineupStatus.RESERVA_NAO_ENTROU);

        // calcula o total de jogos jogados
        Long totalPlayed = starters + subsIn;

        // retorna o DTO com os dados
        return new PlayerParticipationDTO(
                player.getName(), // pega o nome do jogador 
                totalPlayed, 
                starters, 
                subsIn, 
                benchOnly
        );
    }
}