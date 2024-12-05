package com.elkindev.subscriber.resource;

import com.elkindev.subscriber.persistence.entities.ScopeEntity;
import com.elkindev.subscriber.service.impl.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scopes")
public class ScopeController {

    private final ScopeService scopeService;

    @Autowired
    public ScopeController(ScopeService scopeService) {
        this.scopeService = scopeService;
    }

    @PostMapping
    public ResponseEntity<List<ScopeEntity>> createScopes(@RequestParam List<String> scopeNames) {
        List<ScopeEntity> scope = scopeService.createScopes(scopeNames);
        return new ResponseEntity<>(scope, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScopeEntity>> getAllScopes() {
        return ResponseEntity.ok(scopeService.getAllScopes());
    }
}
