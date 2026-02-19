package es.neil.api.mapper;

import es.neil.api.domain.Role;
import es.neil.api.dto.role.RoleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toDto(Role role);
    Role toEntity(RoleDto roleDto);
    List<RoleDto> toDtoList(List<Role> roles);
}
