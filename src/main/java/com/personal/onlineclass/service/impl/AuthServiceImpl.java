package com.personal.onlineclass.service.impl;

import com.personal.onlineclass.constant.UserRole;
import com.personal.onlineclass.dto.request.AuthRequest;
import com.personal.onlineclass.dto.response.LoginResponse;
import com.personal.onlineclass.dto.response.RegisterResponse;
import com.personal.onlineclass.entity.Role;
import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.repository.TeacherRepository;
import com.personal.onlineclass.service.AuthService;
import com.personal.onlineclass.service.JwtService;
import com.personal.onlineclass.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TeacherRepository teacherRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(AuthRequest authRequest) {
        Role role = roleService.getOrSave(UserRole.ROLE_TEACHER);
        String hashPassword = passwordEncoder.encode(authRequest.getPassword());
        Teacher teacher = Teacher.builder()
                .username(authRequest.getUsername())
                .password(hashPassword)
                .role(List.of(role))
                .isEnable(true)
                .build();
        teacherRepository.saveAndFlush(teacher);

        List<String> roles = teacher.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return RegisterResponse.builder().build();
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(authentication);

        Teacher teacher = (Teacher) authenticate.getPrincipal();
        String token = jwtService.generateToken(teacher);
        return LoginResponse.builder()
                .token(token)
                .username(teacher.getUsername())
                .roles(teacher.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
