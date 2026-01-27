package aguia.history.drakes.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// DTO para autenticação
public record AuthenticationDTO(
        @NotBlank @Email String email,
        @NotBlank String password) {
}