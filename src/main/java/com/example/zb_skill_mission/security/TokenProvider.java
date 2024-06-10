package com.example.zb_skill_mission.security;

import com.example.zb_skill_mission.entity.UserEntity;
import com.example.zb_skill_mission.model.UserAuth;
import com.example.zb_skill_mission.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // ms
    private static final String KEY_ROLES = "roles";
    private final UserService userService;
    private final SecretKey secretKey;

    // 아이디와 권한으로 token 생성
    public String generateToken(String username, List<String> roles) {
        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .header()
                .add(KEY_ROLES, roles)
                .and()
                .subject(username)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(secretKey)
                .compact();
    }

    // 토큰에서 Authentication 객체를 리턴한다.
    @Transactional
    public UserAuth getAuthentication(String jwt) {
        UserEntity userEntity =
                userService.loadUserByUsername(getUsername(jwt));
        log.info("UserDetails::" + userEntity);

        return new UserAuth(new UsernamePasswordAuthenticationToken(
                userEntity, "", userEntity.getAuthorities()),
                userEntity.getId());
    }

    // 토큰으로 setSubject했던 아이디 찾기
    public String getUsername(String token) {
        return parseClaims(token).getSubject();
    }

    // 토큰 내용이 있고 유효하면 true
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) return false;

        var claims = parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    // 토큰 검증
    private Claims parseClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build()
                .parseSignedClaims(token).getPayload();
    }
}
