package com.cookmate.cookmate_web.domain.file.controller;

import com.cookmate.cookmate_web.domain.file.entity.FileDetail;
import com.cookmate.cookmate_web.domain.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

/**
 * @file        FileController.java
 * @description 파일 컨트롤러
 * @author      강보람
 * @since       2026-01-24
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-24      강보람       최초 생성
 * </pre>
 */

@Slf4j
@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 파일 업로드
     * @param files 파일 목록
     * @param fileGrpId 파일 그룹 ID
     * @param rgtrKey 등록자 키
     * @return 파일 그룹 ID
     */
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFiles(List<MultipartFile> files, String fileGrpId, String rgtrKey) {
        try {
            String saveFileGrpId = fileService.saveFile(files, fileGrpId, rgtrKey);
            return ResponseEntity.ok(saveFileGrpId);
        } catch (Exception e) {
            log.error("FileController.uploadFiles => {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 파일 다운로드
     * @param fileId 파일 ID
     * @return 파일 다운로드
     * @throws IOException 파일 다운로드 실패 시
     */
    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws IOException {
        FileDetail fileDetail = fileService.getFileDetail(fileId);
        File file = fileService.getFileObject(fileId);
        Resource resource = new FileSystemResource(file);

        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(fileDetail.getFileOrgNm(), StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .contentLength(file.length())
                .body(resource);
    }

    /**
     * 이미지 출력
     * @param fileId 파일 ID
     * @return 이미지 출력
     * @throws IOException 이미지 출력 실패 시
     */
    @GetMapping("/view/{fileId}")
    public ResponseEntity<Resource> viewImage(@PathVariable String fileId) throws IOException {
        File file = fileService.getFileObject(fileId);
        Resource resource = new FileSystemResource(file);

        // 파일의 MIME 타입 추출
        String contentType = Files.probeContentType(file.toPath());

        // 타입을 못 찾으면 기본값 설정
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, contentType);

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    /**
     * 단일 파일 삭제
     * @param fileId 파일 ID
     * @return 파일 삭제 성공 여부
     */
    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok().build();
    }

    /**
     * 파일 그룹 삭제
     * @param fileGrpId 파일 그룹 ID
     * @return 파일 그룹 삭제 성공 여부
     */
    @DeleteMapping("/delete/group/{fileGrpId}")
    public ResponseEntity<Void> deleteFileGroup(@PathVariable String fileGrpId) {
        fileService.deleteFileGroup(fileGrpId);
        return ResponseEntity.ok().build();
    }
}
