package com.edgar.bbs.dao;

import com.edgar.bbs.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long> {
    /*
    @author: Edgar
    对于用户信息的查询操作
     */

    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> findAll();

    @Query(value = "SELECT * FROM user WHERE username=:username", nativeQuery = true)
    List<User> findAllByUsername(@Param("username") String username);
    
    @Query(value = "SELECT * FROM user WHERE id=:id", nativeQuery = true)
    Optional<User> findUserById(@Param("id") Long id);
    
    
}
