package com.edgar.bbs.dao;

import com.edgar.bbs.domain.Carousel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CarouselDao extends JpaRepository<Carousel, Long> {
    /**
     * 轮播图的相关数据库操作
     */
    @Query(value = "SELECT url FROM carousel WHERE active=? ", nativeQuery = true)
    List<Carousel> findAllByActive(Boolean active);


    @Modifying
    @Query(value = "UPDATE carousel SET title=:title, url=:url, active=:active WHERE id=:id", nativeQuery = true)
    void updateCarouselById(Long id);

    @Modifying
    @Query(value = "INSERT INTO carousel(title, url, active) VALUES(:title, :url, :active)", nativeQuery = true)
    void insertCarousel(String title, String url, Boolean active);
}
