package es.neil.api.service;

import es.neil.api.domain.Role;

import java.util.List;

public interface IRoleService {
    Role createRole(String roleName);
    List<Role> findAll();
    Role findByName(String name);
}
