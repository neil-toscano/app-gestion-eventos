package es.neil.api.service;

import es.neil.api.domain.Role;
import es.neil.api.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final IRoleRepository roleRepository;

    @Override
    @Transactional
    public Role createRole(String roleName) {
        if (roleRepository.findByName(roleName).isPresent()) {
            throw new RuntimeException("El rol " + roleName + " ya existe");
        }

        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + name));
    }
}
