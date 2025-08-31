package com.personal.onlineclass.service;

import com.personal.onlineclass.dto.request.AuthRequest;
import com.personal.onlineclass.dto.response.LoginResponse;
import com.personal.onlineclass.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest authRequest);
    LoginResponse login (AuthRequest authRequest);
}
