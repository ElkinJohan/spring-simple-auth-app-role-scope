package com.elkindev.subscriber.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoleRequest {
    private String roleName;
    private Set<Long> scopeIds;
}
