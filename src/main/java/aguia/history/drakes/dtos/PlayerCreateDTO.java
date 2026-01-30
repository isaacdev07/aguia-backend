package aguia.history.drakes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlayerCreateDTO(
    @NotBlank String name,
    @NotBlank String position,
    @NotNull Integer shirtNumber,
    @NotNull Long teamId) {
        
    }
