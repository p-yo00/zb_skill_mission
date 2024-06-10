package com.example.zb_skill_mission.service;

import com.example.zb_skill_mission.entity.UserEntity;
import com.example.zb_skill_mission.model.Auth;
import com.example.zb_skill_mission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("can't find " +
                        "user -> " + username));
    }

    /**
     * db에 사용자 정보를 저장한다.
     */
    public UserEntity register(Auth.SignUp user) {
        log.info("register -> " + user.toString());
        return userRepository.save(UserEntity.toEntity(user));
    }

    /**
     * 로그인 시 회원 정보를 가져와 리턴한다.
     */
    public UserEntity authenticate(Auth.SignIn user) {
        UserEntity loginUser = userRepository.findByEmail(user.getUserId())
                .orElseThrow(() -> new RuntimeException("회원 정보가 틀렸"));

        if (!loginUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("회원 정보가 틀렸");
        }
        log.info("login -> " + loginUser.getEmail());
        return loginUser;
    }
}
