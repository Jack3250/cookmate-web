package com.cookmate.cookmate_web.domain.global.config;

import com.cookmate.cookmate_web.domain.common.entity.CmmnMsg;
import com.cookmate.cookmate_web.domain.common.repository.CmmnMsgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * @file        DatabaseMessageSource.java
 * @description 메세지 소스 설정
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
@Component("messageSource")
@RequiredArgsConstructor
public class DatabaseMessageSource extends AbstractMessageSource {

    private final CmmnMsgRepository cmmnMsgRepository;

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        // DB에서 msg_cd(code)로 메시지 조회
        CmmnMsg message = cmmnMsgRepository.findById(code).orElse(null);

        if (message == null) {
            return null; // 메시지가 없으면 기본값 사용 시도
        }

        // MessageFormat 형식으로 변환하여 반환
        return new MessageFormat(message.getMsgCn(), locale);
    }
}
