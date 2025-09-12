package com.personal.onlineclass.service;

import com.personal.onlineclass.dto.request.AuthRequest;
import com.personal.onlineclass.dto.request.RegisterRequest;
import com.personal.onlineclass.dto.response.LoginResponse;
import com.personal.onlineclass.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login (AuthRequest authRequest);
}
