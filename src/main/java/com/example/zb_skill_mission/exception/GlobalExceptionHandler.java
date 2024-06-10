package com.example.zb_skill_mission.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return new ResponseEntity<>(e.getErrorCode().getDescription(),
                e.getErrorCode().getHttpStatus());
    }

    // @Valid 검증에서 실패했을 때, 유효성 검증 조건에 대한 메시지를 리턴한다.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder messageBuilder = new StringBuilder();
        for (FieldError error : bindingResult.getFieldErrors()) {
            messageBuilder.append(error.getDefaultMessage());
        }

        return new ResponseEntity<>(messageBuilder.toString(), HttpStatus.BAD_REQUEST);
    }

}
