package com.personal.onlineclass.dto.response;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtClaims {
    private String accountId;
    private List<String> roles;
}
