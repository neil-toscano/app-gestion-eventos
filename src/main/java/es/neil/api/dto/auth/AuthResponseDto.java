package es.neil.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Set;

@Data
public class AuthResponseDto {
    @Schema(
            description = "Token JWT que debe enviarse en la cabecera Authorization",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String token;

    @Schema(description = "Nombre del usuario que ha iniciado sesión", example = "neil_admin")
    private String username;

    @Schema(
            description = "Lista de permisos o roles asignados al usuario",
            example = "[\"ROLE_USER\", \"ROLE_ADMIN\"]"
    )
    private Set<String> roles;
}
