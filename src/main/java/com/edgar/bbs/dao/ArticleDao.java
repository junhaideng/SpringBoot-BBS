package com.edgar.bbs.dao;


import com.edgar.bbs.domain.Article;
import com.edgar.bbs.dao.info.SearchArticlesInfo;
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

    @Query(value = "SELECT id, title, content FROM article WHERE title LIKE %:q%", nativeQuery = true)
    List<SearchArticlesInfo> findArticlesTitleContains(String q);

    @Query(value = "SELECT * from article ORDER BY `read` DESC LIMIT 10 OFFSET ?1", nativeQuery = true)
    List<Article> findHotArticle(Integer page);

    @Modifying
    @Query(value = "UPDATE article SET `read`=?1 WHERE id=?2", nativeQuery = true)
    void updateReadById(Long read, Long id);

    @Modifying
    @Query(value = "UPDATE article SET star=?1 WHERE id=?2", nativeQuery = true)
    void updateStarById(Long star, Long id);

    @Modifying
    @Query(value ="UPDATE article SET comments=?1 WHERE id=?2", nativeQuery = true)
    void updateCommentsById(Long comments, Long id);

    @Query(value = "SELECT username FROM article WHERE id=?1", nativeQuery = true)
    String getUsernameById(Long id);

}
