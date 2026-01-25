package com.cookmate.cookmate_web.domain.file.service;

import com.cookmate.cookmate_web.domain.common.util.KeygenUtil;
import com.cookmate.cookmate_web.domain.file.entity.FileDetail;
import com.cookmate.cookmate_web.domain.file.entity.FileGroup;
import com.cookmate.cookmate_web.domain.file.repository.FileDetailRepository;
import com.cookmate.cookmate_web.domain.file.repository.FileGroupRepository;
import com.cookmate.cookmate_web.domain.global.error.CustomException;
import com.cookmate.cookmate_web.domain.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * @file        FileService.java
 * @description 파일 서비스
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

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final FileDetailRepository fileDetailRepository;
    private final FileGroupRepository fileGroupRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 파일 저장
     * @param files 파일
     * @param fileGrpId 파일 그룹 ID
     * @param rgtrKey 등록자 키
     * @return 파일 그룹 ID
     * @throws IOException 파일 저장 실패 시
     */
    @Transactional
    public String saveFile(List<MultipartFile> files, String fileGrpId, String rgtrKey) {

        if (files == null || files.isEmpty()) {
            return fileGrpId;
        }

        FileGroup fileGroup;
        String folderPath;

        // FileGroup 조회 또는 생성
        if (fileGrpId == null) {
            folderPath = getFolder();
            FileGroup newGroup = FileGroup.builder()
                    .fileGrpId(KeygenUtil.generateKey())
                    .filePath(folderPath)
                    .rgtrKey(rgtrKey)
                    .build();
            fileGroup = fileGroupRepository.save(newGroup);
        } else { // 기존 파일 그룹 ID가 있을 경우
            fileGroup = fileGroupRepository.findFileGroup(fileGrpId)
                    .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));
            folderPath = fileGroup.getFilePath();
        }

        // 디렉토리 확인 및 생성
        File directory = new File(uploadDir, folderPath);
        if (!directory.exists()) {
            boolean isCreate = directory.mkdirs();
            if (!isCreate) {
                log.warn("디렉토리 생성 실패: {}", directory.getAbsolutePath());
            }
        }

        // 순번 계산 (기존 개수 + 1)
        int fileOdr = fileDetailRepository.countActiveFiles(fileGroup) + 1;

        // 파일 저장 반복
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }

            String fileOrgNm = file.getOriginalFilename();
            String fileExt = getExtension(fileOrgNm);
            String fileSaveNm = UUID.randomUUID() + "." + fileExt;

            // 물리 저장
            File saveFile = new File(directory, fileSaveNm);
            try {
                file.transferTo(saveFile);
            } catch (IOException e) {
                throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            }

            FileDetail fileDetail = FileDetail.builder()
                    .fileGroup(fileGroup)
                    .fileId(KeygenUtil.generateKey())
                    .fileOrgNm(fileOrgNm)
                    .fileSaveNm(fileSaveNm)
                    .fileExt(fileExt)
                    .fileSize(file.getSize())
                    .fileOdr(fileOdr++)
                    .rgtrKey(rgtrKey)
                    .build();

            fileDetailRepository.save(fileDetail);
        }

        return fileGroup.getFileGrpId();
    }

    /**
     * 파일 상세 조회
     * @param fileId 파일 Id
     * @return 파일 상세 정보
     */
    public FileDetail getFileDetail(String fileId) {
        return fileDetailRepository.findByFileIdAndDelYn(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));
    }

    /**
     * 파일 객체 조회
     * @param fileId 파일 Id
     * @return 파일 객체
     */
    public File getFileObject(String fileId) {
        FileDetail fileDetail = getFileDetail(fileId);
        FileGroup fileGroup = fileDetail.getFileGroup(); // 연관된 그룹 정보

        String fullPath = uploadDir + fileGroup.getFilePath() + File.separator + fileDetail.getFileSaveNm();

        File file = new File(fullPath);
        if (!file.exists()) {
            throw new CustomException(ErrorCode.FILE_NOT_FOUND);
        }
        return file;
    }

    /**
     * 단일 파일 삭제
     * @param fileId 파일 ID
     */
    @Transactional
    public void deleteFile(String fileId) {
        // 이미 삭제된 파일인지 체크
        FileDetail fileDetail = fileDetailRepository.findByFileIdAndDelYn(fileId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        // 파일 삭제 처리
        fileDetail.deleteFile();
    }

    /**
     * 파일 그룹 전체 삭제
     * @param fileGrpId 파일 그룹 ID
     */
    @Transactional
    public void deleteFileGroup(String fileGrpId) {
        // 그룹 존재 확인
        FileGroup fileGroup = fileGroupRepository.findFileGroup(fileGrpId)
                .orElseThrow(() -> new CustomException(ErrorCode.FILE_NOT_FOUND));

        // 하위 파일들 일괄 삭제
        fileDetailRepository.deleteAllByFileGrpSeq(fileGroup.getFileGrpSeq());

        // 그룹 삭제
        fileGroup.deleteFileGrp();
    }

    /**
     * 파일 수정
     * @param fileGrpId 파일 그룹 ID
     * @param newFiles 새로 추가된 파일 목록
     * @param deleteFiles 삭제 요청된 파일 목록
     * @param rgtrKey 등록자 키
     * @throws IOException 파일 저장 실패 시
     */
    @Transactional
    public void updateFiles(String fileGrpId, List<MultipartFile> newFiles, List<String> deleteFiles, String rgtrKey) throws IOException {
        // 삭제 요청된 파일들 처리
        if (deleteFiles != null && !deleteFiles.isEmpty()) {
            for (String fileId : deleteFiles) {
                deleteFile(fileId);
            }
        }

        // 새로 추가된 파일들 저장
        if (newFiles != null && !newFiles.isEmpty()) {
            saveFile(newFiles, fileGrpId, rgtrKey); // 기존 saveFiles 재활용 (그룹ID가 있으므로 추가됨)
        }
    }

    /**
     * 오늘 날짜 경로 추출 함수
     * @return yyyy/MM/dd 형식의 오늘 날짜
     */
    private String getFolder() {
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return dateStr.replace("/", File.separator);
    }

    /**
     * 확장자 추출 함수
     * @param fileName 저장 파일명
     * @return 확장자
     */
    private String getExtension(String fileName) {
        // 확장자 없는 경우 기본값
        if (!StringUtils.hasText(fileName) || fileName.lastIndexOf(".") == -1) {
            return "bin";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

}