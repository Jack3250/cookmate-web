package com.cookmate.cookmate_web.domain.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

/**
 * @file        CmmnMsg.java
 * @description 공통 메시지 관리 엔티티
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
@Table(name = "cmmn_msg")
public class CmmnMsg {

    @Id
    @Column(name = "msg_cd", length = 100)
    private String msgCd; // 메세지 KEY

    @Column(name = "msg_cn", nullable = false, columnDefinition = "TEXT")
    private String msgCn; // 메세지 내용

    @Column(name = "msg_ty", nullable = false, length = 20)
    private String msgTy; // 분류

    @Column(name = "rgtr_key", nullable = false, length = 20)
    private String rgtrKey;

    @CreatedDate
    @Column(name = "reg_dt", updatable = false)
    private LocalDateTime regDt;

    @Column(name = "mdfr_key", length = 20)
    private String mdfrKey;

    @LastModifiedDate
    @Column(name = "mdfcn_dt")
    private LocalDateTime mdfcnDt;

}
