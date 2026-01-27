package aguia.history.drakes.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

// DTO para registro de novos usuários (role definida como USER por padrão)
public record RegisterDTO(
        @NotBlank @Email String email,
        @NotBlank String password, 
        @NotNull String role 
) {
}
