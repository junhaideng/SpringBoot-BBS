package com.edgar.bbs.dao;

import com.edgar.bbs.domain.LoginLog;
import com.edgar.bbs.utils.LoginLogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoginLogDao extends JpaRepository<LoginLog, Long> {
    /*
    对登录日志的查询操作
     */
    @Query(value = "SELECT ip, address, create_time FROM login_log WHERE user_id=:user_id", nativeQuery = true)
    List<LoginLogInfo> findAllByUserId(@Param("user_id") Long user_id);
}
