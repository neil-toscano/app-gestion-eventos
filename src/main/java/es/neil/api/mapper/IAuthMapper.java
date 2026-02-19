package es.neil.api.mapper;

import es.neil.api.domain.Role;
import es.neil.api.domain.User;
import es.neil.api.dto.auth.AuthResponseDto;
import es.neil.api.dto.auth.RegisterRequestDto;
import es.neil.api.dto.user.UserResponseDto;
import es.neil.api.exception.ResourceNotFoundException;
import es.neil.api.repository.IRoleRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {RoleMapper.class, IEventMapper.class})
public abstract class IAuthMapper {

    @Autowired
    protected IRoleRepository roleRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    //@Mapping(target = "roles", ignore = true)
    @Mapping(target = "roles", source = "roles", qualifiedByName = "mapRoleStringsToRoles")
    @Mapping(target = "attendedEvents", ignore = true)
    public abstract User toUser(RegisterRequestDto registerRequestDto);

    public abstract UserResponseDto toUserResponseDto(User user);
    public abstract List<UserResponseDto> toUserListResponseDto(List<User> users);


    @Mapping(target = "token", source = "token")
    @Mapping(target = "roles", expression = "java(mapRoles(user))")
    @Mapping(target = "username", source = "user.username")
    public  abstract  AuthResponseDto toAuthResponseDto(User user, String token);

    protected Set<String> mapRoles(User user) {
        if (user.getRoles() == null) {
            return null;
        }
        return user.getRoles().stream()
                .map(role -> role.getName())
                .collect(Collectors.toSet());
    }

    @Named("mapRoleStringsToRoles")
    public Set<Role> mapRoleStringsToRoles(Set<String> roleNames) {
        if(roleNames == null || roleNames.isEmpty()) {
            return roleRepository.findByName("ROLE_USER")
                    .map(Collections::singleton)
                    .orElseThrow(() -> new ResourceNotFoundException("Error"));
        }

        return roleNames.stream().map(
                roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado"))
        ).collect(Collectors.toSet());
    }
}
