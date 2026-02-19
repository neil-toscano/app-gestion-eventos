package es.neil.api.rest_controller;

import es.neil.api.dto.auth.LoginRequestDto;
import es.neil.api.dto.auth.RegisterRequestDto;
import es.neil.api.dto.auth.AuthResponseDto;
import es.neil.api.dto.event.EventResponseDto;
import es.neil.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @Operation(summary = "Inicio de sesión", description = "Genera un token JWT")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login exitoso",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciales incorrectas",
                    content = @io.swagger.v3.oas.annotations.media.Content
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos de entrada inválidos"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        log.debug("Intento de login para el usuario: {}", loginRequestDto.getUsername());

        return ResponseEntity.ok(authService.login(loginRequestDto));
    }
}
