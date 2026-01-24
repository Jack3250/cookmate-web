package com.cookmate.cookmate_web.domain.users.service;

import com.cookmate.cookmate_web.domain.common.util.KeygenUtil;
import com.cookmate.cookmate_web.domain.global.error.CustomException;
import com.cookmate.cookmate_web.domain.global.error.ErrorCode;
import com.cookmate.cookmate_web.domain.users.dto.SessionUser;
import com.cookmate.cookmate_web.domain.users.dto.UserDTO;
import com.cookmate.cookmate_web.domain.users.entity.User;
import com.cookmate.cookmate_web.domain.users.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @file        UserService.java
 * @description 사용자 정보 API 서비스
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
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HttpSession httpSession;

    /**
     * 회원가입 로직
     * @param request 회원가입 요청 데이터
     * @return 생성된 회원 응답 데이터
     */
    @Transactional
    public UserDTO.Response regist(UserDTO.RegistRequest request) {

        // 아이디 중복 체크
        validateDuplicateLoginId(request.getLoginId());

        // 이메일 중복 체크
        validateDuplicateEmail(request.getEmail());

        // 고유 키 생성 및 비밀번호 암호화 준비
        String userKey = KeygenUtil.generateKey();
        String encPswd = passwordEncoder.encode(request.getPswd());

        // DTO -> Entity 변환
        User user = request.toEntity(encPswd, userKey);

        // DB 저장
        User savedUser = userRepository.save(user);

        // Entity -> Response DTO 변환 후 반환
        return UserDTO.Response.toDTO(savedUser);
    }

    public UserDTO.Response login(UserDTO.LoginRequest loginRequest) {
        // 아이디 없음
        User user = userRepository.findByLoginId(loginRequest.getLoginId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 비교 (입력 비번 vs DB 암호화 비번)
        if (!passwordEncoder.matches(loginRequest.getPswd(), user.getPswd())) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // 세션에 사용자 정보 저장
        httpSession.setAttribute("USER_SESSION", new SessionUser(user));

        return UserDTO.Response.toDTO(user);
    }

    public void validateDuplicateLoginId(String loginId) {
        userRepository.findByLoginId(loginId).ifPresent(m -> {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE, new Object[]{"아이디"});
        });
    }

    public void validateDuplicateEmail(String email) {
        userRepository.findByEmail(email).ifPresent(m -> {
            throw new CustomException(ErrorCode.DUPLICATE_VALUE, new Object[]{"이메일"});
        });
    }
}
