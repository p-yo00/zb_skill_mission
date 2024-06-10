package com.example.zb_skill_mission.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ERROR("error");

    private final String description;
}
