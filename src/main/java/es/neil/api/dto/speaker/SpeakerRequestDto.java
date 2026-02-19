package es.neil.api.dto.speaker;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpeakerRequestDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede exceder los 150 caracteres")
    private String name;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El formato del email no es válido")
    @Size(max = 100, message = "El email no puede exceder los 100 caracteres")
    private String email;

    @Size(max = 2000, message = "La biografía es demasiado larga")
    private String bio;
}
