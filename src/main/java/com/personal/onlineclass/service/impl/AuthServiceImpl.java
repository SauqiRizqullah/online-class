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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TeacherRepository teacherRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public RegisterResponse register(AuthRequest authRequest) {

        log.info("Creating a New Account of Teacher!!!");
        log.info("");
        log.info("Getting a teacher role...");
        Role role = roleService.getOrSave(UserRole.ROLE_TEACHER);
        log.info("Encoding a password...");
        String hashPassword = passwordEncoder.encode(authRequest.getPassword());
        log.info("Building a teacher object...");
        Teacher teacher = Teacher.builder()
                .username(authRequest.getUsername())
                .password(hashPassword)
                .role(List.of(role))
                .isEnable(true)
                .build();
        log.info("Saving teacher account to database...");
        teacherRepository.saveAndFlush(teacher);

        log.info("Getting roles through authorities...");
        List<String> roles = teacher.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        log.info("Returning register response!!!");
        return RegisterResponse.builder()
                .username(teacher.getUsername())
                .roles(roles)
                .build();
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        log.info("Preparing to Log In!!!");
        log.info("");
        log.info("Authentication...");
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        log.info("Authenticating through authentication manager...");
        Authentication authenticate = authenticationManager.authenticate(authentication);

        log.info("Getting account's principal...");
        Teacher teacher = (Teacher) authenticate.getPrincipal();
        log.info("Generating JWT...");
        String token = jwtService.generateToken(teacher);
        log.info("Returning login response!!!");
        return LoginResponse.builder()
                .token(token)
                .username(teacher.getUsername())
                .roles(teacher.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
