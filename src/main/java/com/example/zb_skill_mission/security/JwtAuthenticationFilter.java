package com.example.zb_skill_mission.security;

import com.example.zb_skill_mission.model.UserAuth;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveTokenFromRequest(request);
        // 헤더에 토큰이 있고, 그 토큰이 유효한지 체크
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            // 토큰에서 인증 정보(UserAuth) 가져오기
            UserAuth userAuth = tokenProvider.getAuthentication(token);
            Authentication auth = userAuth.getAuthentication();
            log.info(auth.getName()+"::"+auth.getAuthorities());

            // 인증 정보를 contextHolder, request에 set
            SecurityContextHolder.getContext().setAuthentication(auth);
            request.setAttribute("userId", userAuth.getUserId());
        }
        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
