package com.edgar.bbs.controller;

import com.edgar.bbs.dao.CourseCommentsDao;
import com.edgar.bbs.dao.CourseDao;
import com.edgar.bbs.dao.info.CourseSetInfo;
import com.edgar.bbs.dao.info.SchoolInfo;
import com.edgar.bbs.domain.Course;
import com.edgar.bbs.domain.CourseComments;
import com.edgar.bbs.service.CourseService;
import com.edgar.bbs.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@Api("课程相关")
@RequestMapping("/api/course")
public class CourseController {

    @Resource
    private CourseDao courseDao;

    @Resource
    private CourseCommentsDao courseCommentsDao;

    @Resource
    private CourseService courseService;

    @ApiOperation("获取学院")
    @RequestMapping(value = "/get_all_school", method = RequestMethod.GET)
    public List<SchoolInfo> getAllSchool() {
        return courseDao.getSchool();
    }

    @ApiOperation("获取所有的课程")
    @RequestMapping(value = "/get_all_course", method = RequestMethod.GET)
    public List<CourseSetInfo> getAllCourse(){
        return courseDao.getCourse();
    }

    @ApiOperation("自动获取课程")
    @RequestMapping(value = "/get_by_value", method = RequestMethod.POST)
    public List<CourseSetInfo> getByValue(@RequestParam("value") String value){
        return courseDao.getCourseByValue(value);
    }

    @ApiOperation("根据学院查询课程")
    @RequestMapping(value = "/get_course", method = RequestMethod.POST)
    public List<Course> getCoursesBySchool(@RequestParam("school") String school, @RequestParam("type") String type) {
        return courseService.getCourse(school, type);
    }

    @ApiOperation("根据id查询课程详细信息")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public Optional<Course> getCourseById(@RequestParam("id") Long id) {
        return courseDao.findById(id);
    }

    @ApiOperation("获取课程的评论信息")
    @RequestMapping(value = "/get_course_comments", method = RequestMethod.POST)
    public List<CourseComments> getCourseComments(@RequestParam("id") Long id) {
        return courseCommentsDao.getCourseCommentsByCourseId(id);
    }

    @ApiOperation("写课程的评论信息")
    @RequestMapping(value = "/write_course_comments", method = RequestMethod.POST)
    public Result writeCourseComments(@RequestParam("comment") String comment, @RequestParam("course_id") Long course_id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null || username.trim().equals("")) {
            return new Result(400, "您还没有登录, 请登录");
        } else {
            Optional<CourseComments> courseComments = courseCommentsDao.findCourseCommentsByUsernameAndCourse_id(course_id, username);
            if(courseComments.isPresent()){
                return new Result(400, "已经对该课程进行了评价，不能重复评论");
            }
            try {
                courseCommentsDao.insertCourseComment(comment, course_id, username);
                return new Result(200, "评论成功");
            } catch (Exception e) {
                return new Result(400, "评论失败，请重试");
            }
        }
    }
}
