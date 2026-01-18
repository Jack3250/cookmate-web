package com.cookmate.cookmate_web.domain.common.repository;

import com.cookmate.cookmate_web.domain.common.entity.CmmnMsg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmmnMsgRepository extends JpaRepository<CmmnMsg, String> {
}
