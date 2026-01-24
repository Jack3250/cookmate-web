package com.cookmate.cookmate_web.domain.file.repository;

import com.cookmate.cookmate_web.domain.file.entity.FileGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @file        FileGroupRepository.java
 * @description 파일 그룹 리포지토리
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

@Repository
public interface FileGroupRepository extends JpaRepository<FileGroup, Long> {

    /**
     * 파일 그룹 조회
     * @param fileGrpSeq 파일 그룹 Seq
     * @return 파일 그룹
     */
    @Query("SELECT fg FROM FileGroup fg WHERE fg.fileGrpSeq = :fileGrpSeq AND fg.delYn = 'N'")
    Optional<FileGroup> findActiveFileGroup(@Param("fileGrpSeq") Long fileGrpSeq);
}
