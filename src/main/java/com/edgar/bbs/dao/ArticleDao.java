package com.edgar.bbs.dao;


import com.edgar.bbs.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleDao extends JpaRepository<Article, Long> {

    @Query(value = "SELECT * FROM article WHERE user_id=:userId", nativeQuery = true)
    List<Article> findArticlesByUserId(Long userId);
}
