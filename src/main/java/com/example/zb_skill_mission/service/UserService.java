package com.example.zb_skill_mission.service;

import com.example.zb_skill_mission.entity.UserEntity;
import com.example.zb_skill_mission.exception.UserException;
import com.example.zb_skill_mission.model.Auth;
import com.example.zb_skill_mission.model.constant.ErrorCode;
import com.example.zb_skill_mission.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserEntity loadUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_EXIST_USER));
    }

    /**
     * db에 사용자 정보를 저장한다.
     */
    public UserEntity register(Auth.SignUp user) {
        log.info("회원가입 -> " + user.getEmail());
        return userRepository.save(UserEntity.toEntity(user));
    }

    /**
     * 로그인 시 회원 정보를 가져와 리턴한다.
     */
    public UserEntity authenticate(Auth.SignIn user) {
        UserEntity loginUser = userRepository.findByEmail(user.getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.FAILED_LOGIN));

        if (!loginUser.getPassword().equals(user.getPassword())) {
            throw new UserException(ErrorCode.FAILED_LOGIN);
        }
        log.info("로그인 -> " + loginUser.getEmail());
        return loginUser;
    }
}
