package com.elkindev.subscriber.resource;

import com.elkindev.subscriber.dto.RoleRequest;
import com.elkindev.subscriber.persistence.entities.RoleEntity;
import com.elkindev.subscriber.service.impl.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<RoleEntity> createRole(@RequestParam String roleName) {
        RoleEntity role = roleService.createRole(roleName);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }
    @PostMapping("/roleWithScopes")
    public ResponseEntity<RoleEntity> createRoleWithScopes(@RequestBody RoleRequest request) {
        RoleEntity role = roleService.createRoleWithScopes(request.getRoleName(), request.getScopeIds());
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @PostMapping("/assignScopes")
    public ResponseEntity<Void> assignScopeToRole(@RequestBody RoleRequest request) {
        Set<Long> scopeIds = request.getScopeIds();
        roleService.assignScopeToRole(request.getRoleName(), scopeIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<RoleEntity>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
