package com.personal.onlineclass.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.personal.onlineclass.dto.response.JwtClaims;
import com.personal.onlineclass.entity.Teacher;
import com.personal.onlineclass.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;

@Service
//@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    private final String JWT_SECRET;
    private final String ISSUER;
    private final long JWT_EXPIRATION;

    public JwtServiceImpl(
            @Value("${onlineclass.jwt.secret_key}") String jwtSecret,
            @Value("${onlineclass.jwt.issuer}") String issuer,
            @Value("${onlineclass.jwt.expirationInSecond}") long expiration
    ){
        JWT_SECRET = jwtSecret;
        ISSUER = issuer;
        JWT_EXPIRATION = expiration;
    }

    @Override
    public String generateToken(Teacher teacher) {
        log.info("GENERATING JWT TOKEN FOR TEACHER!!!");
        log.info("");
        try {
            // gunakan algoritma kepada jwt secret
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            log.info("Defining JWT Algorithm");
            return JWT.create()
                    .withIssuedAt(Instant.now()) // kapan dibuatnya (sekarang plus beberapa detik)
                    .withExpiresAt(Instant.now().plusSeconds(JWT_EXPIRATION)) // durasi expirenya
                    .withIssuer(ISSUER)
                    .withSubject(teacher.getTeacherId())
                    .withClaim("roles",teacher.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                    .sign(algorithm);
        } catch (JWTCreationException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while creating JWT Token!!!");
        }
    }

    @Override
    public boolean verifyJwtToken(String token) {
        log.info("VERIFYING JWT TOKEN FOR TEACHER!!!");
        try {
            Algorithm algorithm = Algorithm.HMAC512(JWT_SECRET); // untuk membuktikan apakah JWT token sama
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            jwtVerifier.verify(parseJwt(token));
            return true;
        } catch (JWTVerificationException exception){
            log.error("Invalid JWT Signature/Claims : {} ", exception.getMessage());
            return false;
        }
    }

    @Override
    public JwtClaims getClaimsByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT decodedJWT = jwtVerifier.verify(parseJwt(token));
            return JwtClaims.builder()
                    .accountId(decodedJWT.getId())
                    .roles(decodedJWT.getClaim("roles").asList(String.class))
                    .build();

        } catch (JWTVerificationException exception) {
            log.error("Invalid JWT Signature/Claims : {}", exception.getMessage());
            return null;
        }
    }

    private String parseJwt(String token){
        if (token != null && token.startsWith("Bearer ")) {

            return token.substring(7);
        }
        return null;
    }
}
