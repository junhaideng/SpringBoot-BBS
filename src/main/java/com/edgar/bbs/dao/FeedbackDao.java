package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface FeedbackDao extends JpaRepository<Feedback, Long> {
    @Query(value = "SELECT * FROM feedback", nativeQuery = true)
    List<Feedback> getAll();

    @Query(value = "SELECT * FROM feedback WHERE active=1", nativeQuery = true)
    List<Feedback> getAllActive();

    @Modifying
    @Query(value = "INSERT INTO feedback(username, email, title, content) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    void insert(String username, String email, String title, String content);

    @Modifying
    @Query(value = "UPDATE feedback SET active=0 WHERE id=?1", nativeQuery = true)
    void updateFeedbackById(Long id);  // 处理完成反馈
}
