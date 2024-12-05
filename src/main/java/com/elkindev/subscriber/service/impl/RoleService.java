package com.elkindev.subscriber.service.impl;

import com.elkindev.subscriber.persistence.entities.RoleEntity;
import com.elkindev.subscriber.persistence.entities.ScopeEntity;
import com.elkindev.subscriber.persistence.repository.RoleRepository;
import com.elkindev.subscriber.persistence.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ScopeRepository scopeRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, ScopeRepository scopeRepository) {
        this.roleRepository = roleRepository;
        this.scopeRepository = scopeRepository;
    }

    public RoleEntity createRole(String roleName) {
        if (roleRepository.findByName(roleName).isPresent()) {
            throw new IllegalArgumentException("Role already exists");
        }
        return roleRepository.save(RoleEntity.builder().name(roleName).build());
    }
    public RoleEntity createRoleWithScopes(String roleName, Set<Long> scopeIds) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(roleName);
        Set<ScopeEntity> scopeEntities = new HashSet<>(scopeRepository.findAllById(scopeIds));
        roleEntity.setScopeEntities(scopeEntities);
        return roleRepository.save(roleEntity);
    }

    public void assignScopeToRole(String roleName, Set<Long> scopeIds) {
        RoleEntity role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        Set<ScopeEntity> scopeEntities = new HashSet<>(scopeRepository.findAllById(scopeIds));

        role.setScopeEntities(scopeEntities);
        roleRepository.save(role);
    }

    public List<RoleEntity> getAllRoles() {
        return roleRepository.findAll();
    }
}
