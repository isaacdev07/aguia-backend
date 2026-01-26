package aguia.history.drakes.dtos;

import lombok.Data;
// dto para estatísticas da equipe
@Data
public class TeamStatsDTO {
    
    private int totalMatches;
    private int wins;
    private int penaltyWins;
    private int draws;
    private int losses;
    private int penaltyLosses;
    private int goalsScored;
    private int goalsConceded;
    private int goalBalance; 
}
