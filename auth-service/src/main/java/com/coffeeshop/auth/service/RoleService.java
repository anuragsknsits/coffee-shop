package com.coffeeshop.auth.service;

import com.coffeeshop.auth.entity.Role;

import java.util.List;

public interface RoleService {
    void createRole(Role role);

    void updateRole(Long roleId, Role role);

    void deleteRole(Long roleId);

    List<Role> getAllRole();
}
