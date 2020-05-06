package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;

@Transactional
public interface FilesDao extends JpaRepository<Files, Long> {
    @Query(value = "SELECT * FROM files WHERE user_id=:userId", nativeQuery = true)
    List<Files> findAllByUserId(@Param(value = "userId") Long userId);

    @Modifying
    @Query(value = "INSERT INTO files(description, file_name, path, type, user_id) VALUES(:description, :file_name, :path, :type, :user_id)", nativeQuery = true)
    void insertFile(String description, String file_name, String path, String type, Long user_id);

    @Query(value = "SELECT * FROM files WHERE file_name=:file_name AND user_id=:user_id", nativeQuery = true)
    List<Files> findFilesByFileNameAndUserId(String file_name, Long user_id);
}
