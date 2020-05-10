package com.edgar.bbs.dao;

import com.edgar.bbs.domain.JwcNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface JwcNoticeDao extends JpaRepository<JwcNotice, Long> {
    @Modifying
    @Query(value = "INSERT INTO jwc_notice(title, link, pub_date) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insert(String title, String link, String pub_date);

    @Modifying
    @Query(value = "TRUNCATE jwc_notice", nativeQuery = true)
    void clear();

    @Query(value = "SELECT * FROM jwc_notice", nativeQuery = true)
    List<JwcNotice> findAll();
}
