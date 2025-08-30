package com.personal.onlineclass.repository;

import com.personal.onlineclass.constant.UserRole;
import com.personal.onlineclass.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {
    Optional<Role> findByRole(UserRole userRole);
}
