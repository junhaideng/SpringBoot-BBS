package com.edgar.bbs.dao;


import com.edgar.bbs.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ArticleDao extends JpaRepository<Article, Long> {

    @Query(value = "SELECT * FROM article WHERE user_id=:userId", nativeQuery = true)
    List<Article> findArticlesByUserId(Long userId);

    @Modifying
    @Query(value = "INSERT INTO article(content, type, title, user_id) VALUES(:content, :type, :title, :user_id)", nativeQuery = true)
    void insertArticleByUserId(Long user_id, String title, String type, String content);
}
