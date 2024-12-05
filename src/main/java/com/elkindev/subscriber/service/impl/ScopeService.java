package com.elkindev.subscriber.service.impl;

import com.elkindev.subscriber.persistence.entities.ScopeEntity;
import com.elkindev.subscriber.persistence.repository.ScopeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ScopeService {
    private final ScopeRepository scopeRepository;

    @Autowired
    public ScopeService(ScopeRepository scopeRepository) {
        this.scopeRepository = scopeRepository;
    }

    public List<ScopeEntity> createScopes(List<String> scopeNames) {
        List<ScopeEntity> createdScopes = new ArrayList<>();

        scopeNames.forEach(scopeName -> {
            if (scopeRepository.findByName(scopeName).isPresent()) {
                log.warn("Scope {} already exists", scopeName);
            } else {
                ScopeEntity newScope = ScopeEntity.builder().name(scopeName).build();
                createdScopes.add(scopeRepository.save(newScope));
            }
        });

        return createdScopes;
    }


    public List<ScopeEntity> getAllScopes() {
        return scopeRepository.findAll();
    }
}
