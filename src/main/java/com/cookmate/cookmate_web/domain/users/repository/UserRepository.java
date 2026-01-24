package com.cookmate.cookmate_web.domain.users.repository;

import com.cookmate.cookmate_web.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @file        UserRepository.java
 * @description 사용자 정보 API 레포지토리
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
 
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 로그인 ID로 사용자 조회
     * @param loginId 사용자 로그인 아이디
     * @return Optional<User>
     */
    Optional<User> findByLoginId(String loginId);

    /**
     * 이메일로 사용자 조회
     * @param email 사용자 이메일
     * @return Optional<User>
     */
    Optional<User> findByEmail(String email);
}
