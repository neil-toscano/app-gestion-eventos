package es.neil.api.dto.user;

import es.neil.api.dto.event.EventSummaryDto;
import es.neil.api.dto.role.RoleDto;

import java.util.List;

public class UserResponseDto {
    private Long id;
    private String username;
    private String email;

    private List<RoleDto> roles;
    private List<EventSummaryDto> attendedEvents;
}
