package com.coffeeshop.auth.repository;

import com.coffeeshop.auth.entity.RolePermission;
import com.coffeeshop.auth.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
}