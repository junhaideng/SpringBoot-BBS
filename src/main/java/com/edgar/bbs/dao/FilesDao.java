package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FilesDao extends JpaRepository<Files, Long> {
    @Query(value = "SELECT * FROM files WHERE user_id=:userId", nativeQuery = true)
    List<Files> findAllByUserId(@Param(value = "userId") Long userId);
}
