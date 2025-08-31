package com.personal.onlineclass.repository;

import com.personal.onlineclass.constant.UserRole;
import com.personal.onlineclass.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole userRole);
}
