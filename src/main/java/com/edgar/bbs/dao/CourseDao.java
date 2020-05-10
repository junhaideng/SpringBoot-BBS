package com.edgar.bbs.dao;

import com.edgar.bbs.dao.info.SchoolInfo;
import com.edgar.bbs.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseDao extends JpaRepository<Course, Long> {
    @Query(value = "SELECT DISTINCT school FROM course", nativeQuery = true)
    List<SchoolInfo> getSchool();
}
