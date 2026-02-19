package es.neil.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequestDto {
    @Schema(
            description = "Nombre de usuario o correo electrónico del usuario registrado",
            example = "neil_admin",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @Schema
            (
            description = "Contraseña de acceso a la cuenta",
            example = "Password123!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;
}
