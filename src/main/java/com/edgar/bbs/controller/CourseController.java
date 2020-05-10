package com.edgar.bbs.controller;

import com.edgar.bbs.dao.CourseDao;
import com.edgar.bbs.dao.info.SchoolInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api("课程相关")
@RequestMapping("/api/course")
public class CourseController {

    @Resource
    private CourseDao courseDao;

    @ApiOperation("获取学院")
    @RequestMapping("/get_all_school")
    public List<SchoolInfo> getAllSchool(){
        return courseDao.getSchool();
    }
}
