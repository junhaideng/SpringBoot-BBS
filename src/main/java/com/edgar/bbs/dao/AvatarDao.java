package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface AvatarDao extends JpaRepository<Avatar, Long> {

    @Modifying
    @Query(value = "INSERT INTO avatar( username) VALUES(?1)", nativeQuery = true)
    void insert( String username);

    @Modifying
    @Query(value = "UPDATE avatar SET path=?1 WHERE username=?2", nativeQuery = true)
    void updateByUsername(String path, String username);

    @Query(value = "SELECT * FROM avatar WHERE username=?1", nativeQuery = true)
    Optional<Avatar> getAvatarByUsername(String username);
}
