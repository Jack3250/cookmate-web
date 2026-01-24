package com.cookmate.cookmate_web.domain.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @file        KeygenUtil.java
 * @description 기본키 생성 유틸
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
 
public class KeygenUtil {

    /**
     * 커스텀 키 생성 메서드
     * 형식: 년월일(6) + UUID 앞자리(8) + 시분초(6)
     */
    public static String generateKey() {
        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // 날짜 포맷 (YYMMDD)
        String datePart = now.format(DateTimeFormatter.ofPattern("yyMMdd"));

        // 시간 포맷 (HHmmss)
        String timePart = now.format(DateTimeFormatter.ofPattern("HHmmss"));

        // UUID 앞 8자리 추출
        String uuidPart = UUID.randomUUID().toString().split("-")[0];

        // 최종 키 조합 (총 20자리)
        return datePart + uuidPart + timePart;
    }
}