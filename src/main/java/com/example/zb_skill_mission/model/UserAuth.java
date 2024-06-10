package com.example.zb_skill_mission.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@AllArgsConstructor
@Getter
@Setter
public class UserAuth {
    private Authentication authentication;
    private Long userId;
}
