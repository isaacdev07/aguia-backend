package aguia.history.drakes.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // builder para facilitar a construção do DTO
public class PlayerGeneralStatsDTO {

    private String playerName;
    
    // dados de participação
    private Long matchesPlayed;
    private Long matchesStarter;
    private Long matchesSub;
    
    // total de gols e assistências
    private Long totalGoals;
    private Long totalAssists;
    
}