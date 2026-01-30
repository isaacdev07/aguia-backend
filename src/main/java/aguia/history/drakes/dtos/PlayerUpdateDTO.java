package aguia.history.drakes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// dto para atualizar jogador
public record PlayerUpdateDTO(
    @NotBlank String name,
    @NotBlank String position, 
    @NotNull Integer shirtNumber) {
    
}
