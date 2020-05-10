package com.edgar.bbs.dao;

import com.edgar.bbs.domain.CourseComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CourseCommentsDao  extends JpaRepository<CourseComments, Long> {
    @Query(value = "SELECT * FROM course_comments WHERE course_id=?1", nativeQuery = true)
    List<CourseComments> getCourseCommentsByCourseId(Long course_id);

    @Modifying
    @Query(value = "INSERT INTO course_comments(comment, course_id, username) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertCourseComment(String comment, Long course_id, String username);

    @Modifying
    @Query(value = "UPDATE course_comments SET comment=?1 WHERE course_id=?2", nativeQuery = true)
    void updateCourseComment(String comment, Long course_id);

    @Modifying
    @Query(value = "DELETE FROM course_comments WHERE username=?1", nativeQuery = true)
    void deleteCourseCommentByUsername(String username);

    @Query(value = "SELECT * FROM course_comments WHERE course_id=?1 AND username=?2", nativeQuery = true)
    Optional<CourseComments> findCourseCommentsByUsernameAndCourse_id(Long course_id, String username);
}
