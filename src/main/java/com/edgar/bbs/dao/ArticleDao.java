package com.edgar.bbs.dao;


import com.edgar.bbs.domain.Article;
import com.edgar.bbs.utils.SearchArticlesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ArticleDao extends JpaRepository<Article, Long> {

    @Query(value = "SELECT * FROM article WHERE username=:username", nativeQuery = true)
    List<Article> findArticlesByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO article(content, type, title, username) VALUES(:content, :type, :title, :username)", nativeQuery = true)
    void insertArticleByUsername(String username, String title, String type, String content);

    @Query(value = "SELECT DISTINCT title FROM article WHERE title LIKE %:q%", nativeQuery = true)
    List<SearchArticlesInfo> findArticlesTitleContains(String q);

}
