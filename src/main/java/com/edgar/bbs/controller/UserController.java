package com.edgar.bbs.controller;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.dao.LoginLogDao;
import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.service.UserService;
import com.edgar.bbs.utils.LoginLogInfo;
import com.edgar.bbs.utils.Result;
import com.edgar.bbs.utils.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@Api(value = "用户相关")
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private LoginLogDao loginLogDao;

    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    @Resource
    private ArticleDao articleDao;

    @Resource
    private FilesDao filesDao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public Result login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpServletRequest request) {
        return userService.login(username, password, request);
    }


    @ApiOperation(value = "获取用户登录日志")
    @RequestMapping(value = "/loginlog", method = RequestMethod.POST)
    public List<LoginLogInfo> getUserLoginLog(@RequestParam(value = "user_id") Long user_id) {
        return loginLogDao.findAllByUserId(user_id);
    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public UserInfo getUserInfo(@RequestParam(value = "username") String username) {
        UserInfo user = userDao.getInfoUsingUsername(username);
        System.out.println(user);
        return user;
    }

    @ApiOperation(value = "获取用户的帖子")
    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public List<Article> getUserArticles(@RequestParam(value = "userId") Long userId) {
        return articleDao.findArticlesByUserId(userId);
    }

    @ApiOperation(value = "获取用户的文件信息")
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public List<Files> getUserFiles(@RequestParam(value = "userId") Long userId) {
        return filesDao.findAllByUserId(userId);
    }

    @ApiOperation(value = "删除文件")
    @RequestMapping(value = "/delfiles", method = RequestMethod.POST)
    public Result delFiles(@RequestBody Long[] filesId){
        return userService.deleteFilesById(filesId);
    }

    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public Result uploadFile(MultipartHttpServletRequest request) throws IOException {
        return userService.uploadFile(Objects.requireNonNull(request.getFile("file")), request.getParameter("type"), request.getParameter("description"), 1L);
    }

    @ApiOperation(value = "下载文件")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public Result downloadFile(@Param(value = "file_id") Long file_id, HttpServletResponse response) throws IOException {
        return userService.downloadFile(response, file_id);
    }

    @ApiOperation(value = "修改用户的密码")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateUserPasswordByUsername(@RequestParam(value = "username") String username, @RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword) {
        return userService.updatePassword(username, oldPassword, newPassword);
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        return userService.deleteUser(username, password);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Result signUp(HttpServletRequest request){
        return userService.signUp(request);
    }

    @ApiOperation(value = "发帖")
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Result post(@RequestParam(value = "title") String title, @RequestParam(value="type") String type, @RequestParam(value = "content") String content, @RequestParam("user_id") Long user_id){
        return userService.postArticle(user_id, title, type, content);
    }
}
