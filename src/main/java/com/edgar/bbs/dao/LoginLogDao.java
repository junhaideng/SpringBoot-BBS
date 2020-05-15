package com.edgar.bbs.dao;

import com.edgar.bbs.domain.LoginLog;
import com.edgar.bbs.dao.info.LoginLogInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
@Transactional
public interface LoginLogDao extends JpaRepository<LoginLog, Long> {
    /*
    对登录日志的查询操作
     */
    @Query(value = "SELECT ip, address, create_time FROM login_log WHERE username=:username", nativeQuery = true)
    List<LoginLogInfo> findAllByUsername(@Param("username") String username);

    @Modifying
    @Query(value = "INSERT INTO login_log(address, ip, username) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insert(String address, String ip, String username);
}
