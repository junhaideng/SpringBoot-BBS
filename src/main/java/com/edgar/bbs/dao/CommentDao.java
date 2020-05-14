package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CommentDao extends JpaRepository<Comment, Long> {
    @Query(value = "SELECT * FROM comment WHERE reply_id=?1", nativeQuery = true)
    List<Comment> getCommentsByReply_id(Long reply_id)  ;

    @Modifying
    @Query(value = "INSERT INTO comment(reply_id, `comment`, username) VALUES(?1, ?2, ?3)", nativeQuery = true)
    void insert(Long reply_id, String comment, String username);
}
