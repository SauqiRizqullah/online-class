package com.personal.onlineclass.service.impl;

import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.repository.TeacherRepository;
import com.personal.onlineclass.service.TeacherAccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TeacherAccountDetailsServiceImpl implements UserDetailsService, TeacherAccountDetailsService {

    private final TeacherRepository teacherRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return teacherRepository.findTeacherByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    @Override
    public Teacher getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return teacherRepository.findTeacherByUsername(authentication.getPrincipal().toString())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher account is not found!!!"));
    }

}
