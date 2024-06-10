package com.example.zb_skill_mission.exception;

import com.example.zb_skill_mission.model.constant.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public ReservationException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
