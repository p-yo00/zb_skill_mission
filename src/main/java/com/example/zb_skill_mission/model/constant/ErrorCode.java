package com.example.zb_skill_mission.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_EXIST_RESERVATION("존재하지 않는 예약입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_USER("존재하지 않는 사용자입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_SHOP("존재하지 않는 매장입니다.", HttpStatus.BAD_REQUEST),
    NOT_EXIST_REVIEW("존재하지 않는 리뷰입니다.", HttpStatus.BAD_REQUEST),
    INVALID_TIME("10분 이후 시간부터 예약이 가능합니다.", HttpStatus.BAD_REQUEST),
    ALREADY_EXIST_TIME("해당 시간대에 예약이 존재합니다.", HttpStatus.BAD_REQUEST),
    NOT_AUTHORIZED("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    REJECTED_RESERVATION("매장으로부터 거절된 예약입니다.", HttpStatus.BAD_REQUEST),
    NOT_CONFIRMED_RESERVATION("확정되지 않았던 예약입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_WRITE_REVIEW("이미 작성한 리뷰입니다.", HttpStatus.BAD_REQUEST),
    FAILED_LOGIN("아이디 또는 비밀번호가 일치하지 않습니다.", HttpStatus.UNAUTHORIZED),
    REQUIRE_PARAMETER("위경도 파라미터가 필요합니다.", HttpStatus.BAD_REQUEST);


    private final String description;
    private final HttpStatus httpStatus;
}
