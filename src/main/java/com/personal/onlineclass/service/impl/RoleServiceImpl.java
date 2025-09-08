package com.personal.onlineclass.service.impl;

import com.personal.onlineclass.constant.UserRole;
import com.personal.onlineclass.entity.Role;
import com.personal.onlineclass.repository.RoleRepository;
import com.personal.onlineclass.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(UserRole userRole) {

        log.info("Returning Role!!!");
        return roleRepository.findByRole(userRole)
                .orElseGet(() -> roleRepository.saveAndFlush(Role.builder().userRole(userRole).build()));
    }
}
