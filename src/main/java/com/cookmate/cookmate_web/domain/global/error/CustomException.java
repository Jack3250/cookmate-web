package com.cookmate.cookmate_web.domain.global.error;

import lombok.Getter;

/**
 * @file        CustomException.java
 * @description 커스텀 에러 정의
 * @author      강보람
 * @since       2026-01-18
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-18      강보람       최초 생성
 * </pre>
 */

@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    private final Object[] args; // 메시지 치환 인자

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
        this.args = null;
    }

    public CustomException(ErrorCode errorCode, Object[] args) {
        super(errorCode.getMessageKey());
        this.errorCode = errorCode;
        this.args = args;
    }
}
