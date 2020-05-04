package com.edgar.bbs.dao;

import com.edgar.bbs.domain.User;
import com.edgar.bbs.utils.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional  // 进行修改更新操作的时候需要该注解
public interface UserDao extends JpaRepository<User, Long> {
    /*
    @author: Edgar
    对于用户信息的查询操作
     */

    @Query(value = "SELECT * FROM user", nativeQuery = true)
    List<User> findAll();

    @Query(value = "SELECT * FROM user WHERE username=:username", nativeQuery = true)
    Optional<User> findUserByUsername(@Param("username") String username);
    
    @Query(value = "SELECT * FROM user WHERE id=:id", nativeQuery = true)
    Optional<User> findUserById(@Param("id") Long id);

    @Query(value = "SELECT * FROM user WHERE academy=:academy", nativeQuery = true)
    List<User> findUsersByAcademy(@Param("academy") String academy);

    @Query(value = "SELECT id, username, academy, avatar, email, sex, create_time, grade, age FROM user WHERE username=:username", nativeQuery = true)
    UserInfo getInfoByUsername(@Param(value = "username") String username);

    @Modifying
    @Query(value = "UPDATE user SET password=:password WHERE username=:username", nativeQuery = true)
    void updatePasswordByUsername(@Param(value = "username") String username, @Param(value = "password") String password);
}
