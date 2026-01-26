package com.cookmate.cookmate_web.domain.common.repository;

import com.cookmate.cookmate_web.domain.common.entity.CmmnMsg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @file        CmmnMsgRepository.java
 * @description 공통 메시지 관리 리포지토리
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
public interface CmmnMsgRepository extends JpaRepository<CmmnMsg, String> {
}
