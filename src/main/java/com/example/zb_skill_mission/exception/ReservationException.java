package com.example.zb_skill_mission.exception;

import com.example.zb_skill_mission.model.constant.ErrorCode;
import lombok.*;

@Getter
@Setter
public class ReservationException extends CustomException {

    // 따로 description 입력하지 않으면 ErrorCode의 기본 메시지 출력
    public ReservationException(ErrorCode errorCode) {
        super(errorCode, errorCode.getDescription());
    }
    public ReservationException(ErrorCode errorCode, String description) {
        super(errorCode, description);
    }
}
