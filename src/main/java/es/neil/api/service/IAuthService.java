package es.neil.api.service;

import es.neil.api.dto.auth.AuthResponseDto;
import es.neil.api.dto.auth.LoginRequestDto;
import es.neil.api.dto.auth.RegisterRequestDto;

public interface IAuthService {
    AuthResponseDto register(RegisterRequestDto request);
    AuthResponseDto login(LoginRequestDto request);
}
