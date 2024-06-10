package com.example.zb_skill_mission.exception;

import com.example.zb_skill_mission.model.constant.ErrorCode;
import lombok.*;

@Getter
@Setter
public class UserException extends CustomException {

    // 따로 description 입력하지 않으면 ErrorCode의 기본 메시지 출력
    public UserException(ErrorCode errorCode) {
        super(errorCode, errorCode.getDescription());
    }
}
