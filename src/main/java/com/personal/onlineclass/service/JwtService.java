package com.personal.onlineclass.service;

import com.personal.onlineclass.dto.response.JwtClaims;
import com.personal.onlineclass.entity.Teacher;

public interface JwtService {

    String generateToken(Teacher teacher);

    boolean verifyJwtToken(String token);

    JwtClaims getClaimsByToken(String token);
}
