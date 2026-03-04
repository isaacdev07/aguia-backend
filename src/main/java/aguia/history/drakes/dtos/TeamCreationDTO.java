package aguia.history.drakes.dtos;

import lombok.Data;

@Data
public class TeamCreationDTO {
    private String name;
    private String city;
    private Integer foundedYear;
    private String coachName;
    private String seasonYear;
    private String shieldUrl;
}