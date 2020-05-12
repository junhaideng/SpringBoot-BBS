package com.edgar.bbs.dao;

import com.edgar.bbs.dao.info.CourseSetInfo;
import com.edgar.bbs.dao.info.SchoolInfo;
import com.edgar.bbs.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Long> {
    @Query(value = "SELECT DISTINCT school FROM course", nativeQuery = true)
    List<SchoolInfo> getSchool();

    @Query(value = "SELECT DISTINCT course.course_name FROM course", nativeQuery = true)
    List<CourseSetInfo> getCourse();

    @Query(value = "SELECT DISTINCT course.course_name FROM course WHERE course_name LIKE %?1%", nativeQuery = true)
    List<CourseSetInfo> getCourseByValue(String value);

    @Query(value = "SELECT * FROM course WHERE school=?1", nativeQuery = true)
    List<Course> getCoursesBySchool(String school);

    @Query(value = "SELECT * FROM course WHERE type=?1", nativeQuery = true)
    List<Course> getCoursesByType(String type);

    @Query(value = "SELECT * FROM course WHERE school=?1 AND type=?2", nativeQuery = true)
    List<Course> getCoursesBySchoolAndType(String school, String type);
}
