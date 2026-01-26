package com.cookmate.cookmate_web.domain.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 강보람
 * @version 1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-18      강보람       최초 생성
 * </pre>
 * @file ErrorCode.java
 * @description 에러코드 정의
 * @since 2026-01-18
 */

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 로그인 실패
    USER_NOT_FOUND(400, "valid.user.login.fail")
    , PASSWORD_NOT_MATCH(400, "valid.user.login.fail")

    // 입력값 중복
    , DUPLICATE_VALUE(400, "valid.common.duplicate")

    // 잘못된 입력값
    , INVALID_INPUT_VALUE(400, "valid.common.invalid")

    // 서버 오류
    , INTERNAL_SERVER_ERROR(500, "valid.common.server.error")
    ;

    private final int status;
    private final String messageKey; // DB의 msg_cd와 매칭
}
