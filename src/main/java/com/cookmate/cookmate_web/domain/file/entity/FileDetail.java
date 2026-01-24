package com.cookmate.cookmate_web.domain.file.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @file        FileDetail.java
 * @description 파일 상세 엔티티
 * @since       2026-01-22
 * @author      강보람
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-22      강보람       최초 생성
 * </pre>
 */

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "file_detail")
@EntityListeners(AuditingEntityListener.class)
public class FileDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_seq", nullable = false)
    private Long fileSeq;

    @Column(name = "file_id", nullable = false, length = 20)
    private String fileId;

    @Column(name = "file_org_nm", nullable = false)
    private String fileOrgNm;

    @Column(name = "file_save_nm", nullable = false)
    private String fileSaveNm;

    @Column(name = "file_ext", nullable = false, length = 10)
    private String fileExt;

    @ColumnDefault("0")
    @Column(name = "file_size", nullable = false)
    @Builder.Default
    private Long fileSize = 0L;

    @ColumnDefault("1")
    @Column(name = "file_odr", nullable = false)
    @Builder.Default
    private Integer fileOdr = 1;

    @Column(name = "rgtr_key", nullable = false, length = 20)
    private String rgtrKey;

    @Column(name = "reg_dt", updatable = false)
    @CreatedDate
    private LocalDateTime regDt;

    @Column(name = "del_yn", nullable = false, length = 1)
    @ColumnDefault("'N'")
    @Builder.Default
    private String delYn = "N";

    // 파일 삭제를 위한 delYn 상태값 변경
    public void deleteFile() {
        this.delYn = "Y";
        this.delDt = LocalDateTime.now();
    }

    // 파일그룹 조인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_grp_seq", nullable = false)
    private FileGroup fileGroup;

}