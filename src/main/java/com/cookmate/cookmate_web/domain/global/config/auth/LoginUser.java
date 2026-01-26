package com.cookmate.cookmate_web.domain.global.config.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @file        LoginUser.java
 * @description 로그인 유저 정보 조회 커스텀 어노테이션
 * @author      강보람
 * @since       2026-01-26
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-26      강보람       최초 생성
 * </pre>
 */

@Target(ElementType.PARAMETER) // 파라미터에만 사용
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginUser {
}
