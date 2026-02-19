package es.neil.api.dto.event;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class EventRequestDto {
    @NotBlank(message = "El campo nombre no puede estar vacio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotNull(message = "La fecha es obligatoria")
    @FutureOrPresent(message = "La fecha no puede ser en el pasado")
    private LocalDate date;

    @NotBlank(message = "La ubicación es obligatoria")
    @Size(min = 5, max = 200, message = "La ubicación debe ser más descriptiva (mínimo 5 caracteres)")
    private String location;

    @NotNull(message = "La categoria es obligatoria")
    private Long categoryId;


    private Set<Long> speakersIds;
}
