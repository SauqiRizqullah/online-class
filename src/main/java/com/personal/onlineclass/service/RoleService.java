package com.personal.onlineclass.service;

import com.personal.onlineclass.constant.UserRole;
import com.personal.onlineclass.entity.Role;

public interface RoleService {
    Role getOrSave(UserRole userRole);
}
