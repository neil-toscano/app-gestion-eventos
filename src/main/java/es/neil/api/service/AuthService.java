package es.neil.api.service;

import es.neil.api.repository.IRoleRepository;
import es.neil.api.security.jwt.JwtService;
import es.neil.api.domain.User;
import es.neil.api.dto.auth.LoginRequestDto;
import es.neil.api.dto.auth.RegisterRequestDto;
import es.neil.api.dto.auth.AuthResponseDto;
import es.neil.api.mapper.IAuthMapper;
import es.neil.api.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final IAuthMapper authMapper;
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final IRoleRepository roleRepository;

    public AuthResponseDto register(RegisterRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = authMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        /* SE AGREGO EL ABSTRACT CLASS EL SE ENCARGA DE DAR ROL AL TRANSFORMAR A ENTITY USER
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Error: El rol USER no existe en la tabla roles."));

        user.getRoles().add(userRole);
        */
        userRepository.save(user);

        String token = getJwtToken(user);

        return authMapper.toAuthResponseDto(user, token);
    }

    public AuthResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = getJwtToken(user);

        return authMapper.toAuthResponseDto(user, token);
    }

    private String getJwtToken(User user) {
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new org.springframework.security.core.authority.SimpleGrantedAuthority(role.getName()))
                .toList();

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .build();

        return jwtService.generateToken(userDetails);
    }
}