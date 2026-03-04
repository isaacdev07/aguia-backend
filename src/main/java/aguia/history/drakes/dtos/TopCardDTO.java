package aguia.history.drakes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopCardDTO {

    // dados do jogador com mais cartões
    private String playerName;
    private Long yellowCards;
    private Long redCards;
    private Long totalCards; 
}