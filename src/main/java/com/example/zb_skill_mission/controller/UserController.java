package com.example.zb_skill_mission.controller;

import com.example.zb_skill_mission.entity.UserEntity;
import com.example.zb_skill_mission.model.Auth;
import com.example.zb_skill_mission.security.TokenProvider;
import com.example.zb_skill_mission.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid Auth.SignUp user) {
        return ResponseEntity.ok(userService.register(user));
    }

    /**
     * 로그인 - 토큰을 생성하여 반환한다.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody @Valid Auth.SignIn request) {
        UserEntity member = userService.authenticate(request);
        // 인증 성공 시 토큰 생성 후 반환
        String token = tokenProvider.generateToken(member.getEmail(),
                member.getRoles());

        return ResponseEntity.ok(token);
    }
}
