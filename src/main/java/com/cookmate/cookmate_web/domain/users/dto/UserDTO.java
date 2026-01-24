package com.cookmate.cookmate_web.domain.users.dto;

import com.cookmate.cookmate_web.domain.users.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @file        UserDTO.java
 * @description 사용자 정보 DTO
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
public class UserDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class RegistRequest { // 회원가입 요청 데이터

        @NotBlank(message = "{valid.user.id.required}")
        @Pattern(regexp = "^[a-z0-9]{4,20}$", message = "{valid.user.id.pattern}")
        private String loginId;

        @NotBlank(message = "{valid.user.pswd.required}")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,16}$", message = "{valid.user.pswd.pattern}")
        private String pswd;

        @NotBlank(message = "{valid.user.nm.required}")
        private String userNm;

        @NotBlank(message = "{valid.user.nickname.required}")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "{valid.user.nickname.pattern}")
        private String nickname;

        @NotBlank(message = "{valid.user.email.required}")
        @Email(message = "{valid.user.email.format}")
        private String email;

        @NotBlank(message = "{valid.user.phone.required}")
        private String telPhone;

        @NotBlank(message = "{valid.user.gender.required}")
        private String gender;

        @NotNull(message = "{valid.user.brth.required}")
        private LocalDateTime userBrth;

        private String zipCd;
        private String addr;
        private String addrDtl;

        public User toEntity(String encPswd, String userKey) {
            return User.builder()
                    .loginId(this.loginId)
                    .userKey(userKey)
                    .userNm(this.userNm)
                    .userBrth(this.userBrth)
                    .telPhone(this.telPhone)
                    .gender(this.gender)
                    .pswd(encPswd)
                    .email(this.email)
                    .nickname(this.nickname)
                    .zipCd(this.zipCd)
                    .addr(this.addr)
                    .addrDtl(this.addrDtl)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Response { // 회원 정보 응답 데이터
        private String userKey;
        private String loginId;
        private String userNm;
        private String nickname;
        private String email;
        private LocalDateTime cretDt;

        // Entity -> DTO 변환
        public static Response toDTO(User user) {
            return Response.builder()
                    .userKey(user.getUserKey())
                    .loginId(user.getLoginId())
                    .userNm(user.getUserNm())
                    .nickname(user.getNickname())
                    .email(user.getEmail())
                    .cretDt(user.getCretDt())
                    .build();
        }
    }
}
