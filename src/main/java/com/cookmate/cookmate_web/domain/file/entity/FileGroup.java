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
 * @file        FileGroup.java
 * @description 파일 그룹 엔티티
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
@Table(name = "file_group")
@EntityListeners(AuditingEntityListener.class)
public class FileGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_grp_seq", nullable = false)
    private Long fileGrpSeq;

    @Column(name = "file_grp_id", unique = true, nullable = false, length = 20)
    private String fileGrpId;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    @Column(name = "rgtr_key", nullable = false, length = 20)
    private String rgtrKey;

    @Column(name = "reg_dt", updatable = false)
    @CreatedDate
    private LocalDateTime regDt;

    @Column(name = "del_yn", nullable = false, length = 1)
    @ColumnDefault("'N'")
    @Builder.Default
    private String delYn = "N";

}