package com.cookmate.cookmate_web.domain.global.config.auth;

import com.cookmate.cookmate_web.domain.global.error.CustomException;
import com.cookmate.cookmate_web.domain.global.error.ErrorCode;
import com.cookmate.cookmate_web.domain.users.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @file        LoginUserArgumentResolver.java
 * @description 로그인 사용자 인수 리졸버
 * @author      강보람
 * @since       2026-01-26
 * @version     1.0
 *
 * <pre>
 * 수정일          수정자          수정내용
 * ----------    ----------    ---------------------------
 * 2026-01-26      강보람       최초 생성
 * </pre>
 */

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession session;

    /**
     * LoginUser 어노테이션이 붙어 있고, 파라미터 클래스 타입이 SessionUser.class인 경우 true 반환
     * @param parameter 파라미터
     * @return true or false
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    /**
     * 파라미터에 전달할 객체를 생성
     * @param parameter 파라미터
     * @param mavContainer 뷰 컨테이너
     * @param webRequest 웹 요청
     * @param binderFactory 바인더 팩토리
     * @return 전달할 객체
     * @throws Exception 예외
     */
    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        SessionUser user = (SessionUser) session.getAttribute("USER_SESSION");
        if (user == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        return user;
    }
}
