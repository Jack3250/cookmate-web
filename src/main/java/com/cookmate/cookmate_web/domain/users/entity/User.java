package com.cookmate.cookmate_web.domain.users.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @file        User.java
 * @description 사용자 정보 엔티티
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_seq", nullable = false)
    private Long id;

    @Column(name = "login_id", unique = true, nullable = false, length = 20)
    private String loginId;

    @Column(name = "user_key", unique = true, nullable = false, length = 20)
    private String userKey;

    @Column(name = "user_nm", nullable = false, length = 50)
    private String userNm;

    @Column(name = "user_brth", nullable = false)
    private LocalDateTime userBrth;

    @Column(name = "zip_cd", length = 10)
    private String zipCd;

    @Column(name = "addr")
    private String addr;

    @Column(name = "addr_dtl")
    private String addrDtl;

    @Column(name = "tel_phone", nullable = false, length = 20)
    private String telPhone;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "pswd", nullable = false, length = 60)
    private String pswd;

    @Column(name = "pswd_chg_dt")
    private LocalDateTime pswdChgDt;

    @Column(name = "pswd_err_cnt", nullable = false)
    @Builder.Default
    private Integer pswdErrCnt = 0;

    @Column(name = "last_lgn_dt")
    private LocalDateTime lastLgnDt;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nickname", unique = true, nullable = false, length = 50)
    private String nickname;

    @Column(name = "file_grp_id", length = 20)
    private String fileGrpId;

    @CreatedDate
    @Column(name = "cret_dt", updatable = false)
    private LocalDateTime cretDt;

    @LastModifiedDate
    @Column(name = "mdfcn_dt")
    private LocalDateTime mdfcnDt;

    @ColumnDefault("'N'")
    @Column(name = "del_yn", nullable = false, length = 1)
    @Builder.Default
    private String delYn = "N";

    @Column(name = "del_dt")
    private LocalDateTime delDt;

    @Column(name = "mdfr_key", length = 20)
    private String mdfrKey;

}