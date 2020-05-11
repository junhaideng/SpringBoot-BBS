package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Files;
import com.edgar.bbs.dao.info.SearchFilesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface FilesDao extends JpaRepository<Files, Long> {
    @Query(value = "SELECT * FROM files WHERE username=:username", nativeQuery = true)
    List<Files> findAllByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO files(description, file_name, path, type, username) VALUES(:description, :file_name, :path, :type, :username)", nativeQuery = true)
    void insertFile(String description, String file_name, String path, String type, String username);

    @Query(value = "SELECT * FROM files WHERE file_name=:file_name AND username=:username", nativeQuery = true)
    List<Files> findFilesByFileNameAndUsername(String file_name, String username);

    @Query(value = "SELECT DISTINCT file_name FROM files WHERE file_name LIKE %:q%", nativeQuery = true)
    List<SearchFilesInfo> findFileNameContains(String q);

    @Query(value = "SELECT * FROM files WHERE id=:id AND username=:username", nativeQuery = true)
    Optional<Files> findByIdAndUsername(Long id, String username);

    @Modifying
    @Query(value = "UPDATE files SET download_times=?1 WHERE id=?2", nativeQuery = true)
    void updateDownloadTimesById(Long downloadTimes, Long id);


}
