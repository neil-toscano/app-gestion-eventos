package es.neil.api.rest_controller;

import es.neil.api.domain.Role;
import es.neil.api.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final IRoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.findAll());
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody String roleName) {
        return new ResponseEntity<>(roleService.createRole(roleName), HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Role> getRoleByName(@PathVariable String name) {
        return ResponseEntity.ok(roleService.findByName(name));
    }
}