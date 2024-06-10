package com.example.zb_skill_mission.exception;

import com.example.zb_skill_mission.model.constant.ErrorCode;
import lombok.*;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String description;
}
