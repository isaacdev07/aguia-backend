package aguia.history.drakes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerAssistDTO {

    private String playerName;
    private Long totalAssists;

}
