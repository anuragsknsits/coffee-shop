package com.coffeeshop.auth.controller;

import com.coffeeshop.auth.entity.Role;
import com.coffeeshop.auth.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody Role role) {
        roleService.createRole(role);
        return ResponseEntity.ok("Role created successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{roleId}")
    public ResponseEntity<String> updateRole(@PathVariable Long roleId, @RequestBody Role role) {
        roleService.updateRole(roleId, role);
        return ResponseEntity.ok("Role updated successfully");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok("Role deleted successfully");
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRole() {
        return ResponseEntity.ok(roleService.getAllRole());
    }
}

