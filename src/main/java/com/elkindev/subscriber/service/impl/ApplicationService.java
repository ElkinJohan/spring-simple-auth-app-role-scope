package com.elkindev.subscriber.service.impl;

import com.elkindev.subscriber.persistence.entities.ApplicationEntity;
import com.elkindev.subscriber.persistence.entities.RoleEntity;
import com.elkindev.subscriber.persistence.repository.ApplicationRepository;
import com.elkindev.subscriber.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository, RoleRepository roleRepository) {
        this.applicationRepository = applicationRepository;
        this.roleRepository = roleRepository;
    }

    public ApplicationEntity registerApplication(String name, String clientSecret) {
        ApplicationEntity app = new ApplicationEntity();
        app.setName(name);
        app.setClientSecret(clientSecret);
        return applicationRepository.save(app);
    }

    public void assignRoleToApplication(Long appId, Long roleId) {
        ApplicationEntity app = applicationRepository.findById(appId)
                .orElseThrow(() -> new IllegalArgumentException("ApplicationEntity not found"));
        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("RoleEntity not found"));
        app.getRoleEntities().add(roleEntity);
        applicationRepository.save(app);
    }

    public List<ApplicationEntity> getAllApps(){
        return this.applicationRepository.findAll();
    }

}
