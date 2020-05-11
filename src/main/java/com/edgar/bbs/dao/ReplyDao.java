package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface ReplyDao extends JpaRepository<Reply, Long> {
    @Query(value = "SELECT * FROM reply WHERE article_id=?1", nativeQuery = true)
    public List<Reply> findAllByArticleId(Long article_id);

    @Modifying
    @Query(value = "INSERT INTO reply(article_id, reply, username) VALUES(?1, ?2, ?3) ", nativeQuery = true)
    public void insertReply(Long article_id, String reply, String username);

    @Modifying
    @Query(value = "UPDATE reply SET reply=?1 WHERE username=?2", nativeQuery = true)
    public void updateReplyByUsername(String reply, String username);

    @Modifying
    @Query(value = "DELETE FROM reply WHERE username=?1", nativeQuery = true)
    public void deleteReplyByUsername(String username);

    @Query(value = "SELECT * FROM reply WHERE article_id=?1 AND username=?2", nativeQuery = true)
    public Optional<Reply> findReplyByArticleIdAndUsername(Long article_id, String username);

    @Query(value = "SELECT COUNT(*) FROM reply WHERE reply.article_id=?1", nativeQuery = true)
    Long getReplyNumByArticleId(Long id);
}
