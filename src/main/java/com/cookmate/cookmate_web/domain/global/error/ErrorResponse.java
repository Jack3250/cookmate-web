package com.cookmate.cookmate_web.domain.global.error;

import lombok.Getter;

import java.util.List;

/**
 * @file        ErrorResponse.java
 * @description 에러 메세지 응답 객체 정의
 * @author      강보람
 * @since       2026-01-17
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-17      강보람       최초 생성
 * </pre>
 */

@Getter
public class ErrorResponse {
    private final String message;
    private final int status;
    private final List<FieldError> errors;

    private ErrorResponse(String message, int status, List<FieldError> errors) {
        this.message = message;
        this.status = status;
        this.errors = errors;
    }

    public static ErrorResponse of(String message, int status) {
        return new ErrorResponse(message, status, List.of());
    }

    public static ErrorResponse of(String message, int status, List<FieldError> errors) {
        return new ErrorResponse(message, status, errors);
    }

    @Getter
    public static class FieldError {
        private final String field;
        private final String value;
        private final String reason;

        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}
