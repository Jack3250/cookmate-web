package com.cookmate.cookmate_web.domain.users.dto;

import com.cookmate.cookmate_web.domain.users.entity.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * @file        SessionUser.java
 * @description 세션 유저 정보
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
public class SessionUser implements Serializable {
    private final String loginId;
    private final String userNm;
    private final String nickname;
    private final String userKey;
    private final String email;

    public SessionUser(User user) {
        this.loginId = user.getLoginId();
        this.userNm = user.getUserNm();
        this.nickname = user.getNickname();
        this.userKey = user.getUserKey();
        this.email = user.getEmail();
    }
}
