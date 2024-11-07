package com.app.faksfit.repository;

import com.app.faksfit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByRoleName(String roleName);

    Role findByRoleId(Long roleId);
}
