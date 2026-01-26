package com.cookmate.cookmate_web.domain.users.controller;

import com.cookmate.cookmate_web.domain.users.dto.UserDTO;
import com.cookmate.cookmate_web.domain.users.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @file        UserController.java
 * @description 사용자 정보 API 컨트롤러
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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원가입 API
     * @param request 사용자가 입력한 회원 정보
     * @return 생성된 회원 정보
     */
    @PostMapping("/regist")
    public ResponseEntity<UserDTO.Response> regist(@Valid @RequestBody UserDTO.RegistRequest request) {
        UserDTO.Response response = userService.regist(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 로그인 API
     * @param loginRequest 사용자가 입력한 아이디, 비밀번호 정보
     * @return 로그인 회원 정보
     */
    @PostMapping("/login")
    public ResponseEntity<UserDTO.Response> login(@Valid @RequestBody UserDTO.LoginRequest loginRequest) {
        UserDTO.Response response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * 로그아웃 API
     * @param session 세션
     * @return 로그아웃 메시지
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // 세션 삭제
        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

}
