package com.coffeeshop.auth.repository;

import com.coffeeshop.auth.entity.EmployeeRoleAssignment;
import com.coffeeshop.auth.entity.EmployeeRoleAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRoleAssignmentRepository extends JpaRepository<EmployeeRoleAssignment, EmployeeRoleAssignmentId> {
    /*void deleteByUserDTO_Id(Long userId);*/
}
