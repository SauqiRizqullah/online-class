package com.personal.onlineclass.security;

import com.personal.onlineclass.dto.response.JwtClaims;
import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.service.JwtService;
import com.personal.onlineclass.service.TeacherService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final TeacherService teacherService;
    final String AUTH_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            String bearerToken = request.getHeader(AUTH_HEADER); // mengambil request client (httpservletrequest)

            if (bearerToken != null && jwtService.verifyJwtToken(bearerToken)) {
                JwtClaims jwtClaims = jwtService.getClaimsByToken(bearerToken);
                log.info("Get JWT Claims: {}", jwtClaims);
                Teacher teacher = teacherService.getById(jwtClaims.getAccountId());
                log.info("Authenticated User: {}", teacher);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        teacher.getUsername(),
                        null,
                        teacher.getAuthorities()
                );
                log.info("Authentication: {}", authentication);

                authentication.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Set User Authentication for {} - {}", teacher.getTeacherId(), teacher.getUsername());
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
