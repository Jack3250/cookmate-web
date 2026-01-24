package com.cookmate.cookmate_web.domain.file.repository;

import com.cookmate.cookmate_web.domain.file.entity.FileDetail;
import com.cookmate.cookmate_web.domain.file.entity.FileGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @file        FileDetailRepository.java
 * @description 파일 상세 리포지토리
 * @author      강보람
 * @since       2026-01-22
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-22      강보람       최초 생성
 * </pre>
 */

@Repository
public interface FileDetailRepository extends JpaRepository<FileDetail, Long> {

    /**
     * 파일 그룹에 파일 갯수 조회
     * @param fileGroup 파일 그룹
     * @return 파일의 개수
     */
    @Query("SELECT COUNT(fileDetail) FROM FileDetail fileDetail WHERE fileDetail.fileGroup = :fileGroup AND fileDetail.delYn = 'N'")
    int countActiveFiles(@Param("fileGroup") FileGroup fileGroup);

    /**
     * 파일 목록 조회
     * @param fileGroup 파일 그룹
     * @return 파일 목록
     */
    @Query("SELECT fileDetail FROM FileDetail fileDetail WHERE fileDetail.fileGroup = :fileGroup AND fileDetail.delYn = 'N' ORDER BY fileDetail.fileOdr ASC")
    List<FileDetail> findAllActiveFiles(@Param("fileGroup") FileGroup fileGroup);

    /**
     * 파일 상세 조회
     * @param fileId 파일 ID
     * @return 파일 상세
     */
    @Query("SELECT fileDetail FROM FileDetail fileDetail WHERE fileDetail.fileId = :fileId AND fileDetail.delYn = 'N'")
    Optional<FileDetail> findByFileIdAndDelYn(@Param("fileId") String fileId);

    /**
     * 파일 그룹에 해당하는 파일 삭제
     * @param fileGrpSeq 파일 그룹 Seq
     */
    @Modifying
    @Query("UPDATE FileDetail fileDetail SET fileDetail.delYn = 'Y' WHERE fileDetail.fileGroup.fileGrpSeq = :fileGrpSeq")
    void deleteAllByFileGrpSeq(@Param("fileGrpSeq") Long fileGrpSeq);
}
