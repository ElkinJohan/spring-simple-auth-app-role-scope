package com.elkindev.subscriber.resource;

import com.elkindev.subscriber.dto.ApplicationRequest;
import com.elkindev.subscriber.persistence.entities.ApplicationEntity;
import com.elkindev.subscriber.service.impl.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApplicationEntity> registerApplication(@RequestBody ApplicationRequest request) {
        ApplicationEntity app = applicationService.registerApplication(request.getName(), request.getClientSecret());
        return ResponseEntity.ok(app);
    }

    @PostMapping("/assignRole/{appId}/roles/{roleId}")
    public ResponseEntity<Void> assignRoleToApplication(@PathVariable Long appId, @PathVariable Long roleId) {
        applicationService.assignRoleToApplication(appId, roleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ApplicationEntity>> getAllApps(){
        return ResponseEntity.ok(this.applicationService.getAllApps());
    }
}
