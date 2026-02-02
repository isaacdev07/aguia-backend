package aguia.history.drakes.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
// dto para update de partida
public record MatchUpdateDTO(@NotNull LocalDateTime date,
                             @NotBlank String opponent,
                             @NotBlank String location,
                             Integer goalsFor,
                             Integer goalsAgainst) {
    
}
