package com.edgar.bbs.service;

import com.edgar.bbs.dao.CourseDao;
import com.edgar.bbs.domain.Course;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    @Resource
    private CourseDao courseDao;

    public List<Course> getCourse(String school, String type){
        if(school.equals("") && type.equals("")){
            return new ArrayList<>();
        }
        if(school.equals("")){
            return courseDao.getCoursesByType(type);
        }
        if(type.equals("")){
            return courseDao.getCoursesBySchool(school);
        }
        return courseDao.getCoursesBySchoolAndType(school, type);
    }
}
