package aguia.history.drakes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerParticipationDTO {
    private String playerName; // nome do jogador
    private Long totalMatches;   // total de jogos que o jogador participou
    private Long matchesAsStarter; // total de jogos como titular
    private Long matchesAsSub;     // total de jogos entrando como substituto
    private Long matchesOnBench;   // total de jogos em que o jogador estava no banco, mas não entrou em campo
}