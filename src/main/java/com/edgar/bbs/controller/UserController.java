package com.edgar.bbs.controller;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.dao.LoginLogDao;
import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.service.UserService;
import com.edgar.bbs.utils.LoginLogInfo;
import com.edgar.bbs.utils.MessageSettingsInfo;
import com.edgar.bbs.utils.Result;
import com.edgar.bbs.utils.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    public List<LoginLogInfo> getUserLoginLog(HttpSession session) {
        return loginLogDao.findAllByUsername((String) session.getAttribute("username"));
    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public UserInfo getUserInfo(HttpSession session) {
        UserInfo user = userDao.getInfoUsingUsername((String) session.getAttribute("username"));
        return user;
    }

    @ApiOperation(value = "修改用户信息")
    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
    public Result updateUserInfo(HttpServletRequest request){
        return userService.updateUserInfo(request);
    }

    @ApiOperation(value = "获取用户的帖子")
    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public List<Article> getUserArticles(HttpSession session) {
        return articleDao.findArticlesByUsername((String) session.getAttribute("username"));
    }

    @ApiOperation(value = "获取用户的文件信息")
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public List<Files> getUserFiles(HttpSession session) {
        return filesDao.findAllByUsername((String) session.getAttribute("username"));
    }

    @ApiOperation(value = "删除文件")
    @RequestMapping(value = "/delfiles", method = RequestMethod.POST)
    public Result delFiles(@RequestBody Long[] filesId, HttpSession session) {
        return userService.deleteFilesByIdAndUsername(filesId, (String) session.getAttribute("username"));

    }

    @ApiOperation(value = "上传文件")
    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public Result uploadFile(MultipartHttpServletRequest request, HttpSession session) throws IOException {
        return userService.uploadFile(Objects.requireNonNull(request.getFile("file")), request.getParameter("type"), request.getParameter("description"), (String) session.getAttribute("username"));
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
    public Result deleteUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,HttpSession session) {
        String real = session.getAttribute("username").toString();
        return userService.deleteUser(username, password, real);
    }

    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Result signUp(HttpServletRequest request) {
        return userService.signUp(request);
    }

    @ApiOperation(value = "发帖")
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public Result post(@RequestParam(value = "title") String title, @RequestParam(value = "type") String type, @RequestParam(value = "content") String content, HttpSession session) {
        return userService.postArticle((String) session.getAttribute("username"), title, type, content);
    }

    @ApiOperation("获取信息设置")
    @RequestMapping(value = "/messageSettings", method = RequestMethod.POST)
    public MessageSettingsInfo getMessageSettingsByUsername(HttpSession session) {
        return userService.getMessageSettingsByUsername((String) session.getAttribute("username"));

    }

    @ApiOperation("修改信息设置")
    @RequestMapping(value = "/changesettings", method = RequestMethod.POST)
    public Result setMessageSettingsByUsername(@RequestBody(required = true) Map<String, Object> map, HttpSession session) {
        System.out.println(map.get("comment").getClass());
        Boolean comment = (Boolean) map.get("comment");
        Boolean like = (Boolean) map.get("like");
        Boolean star = (Boolean) map.get("star");
        String username = (String) session.getAttribute("username");
        return userService.updateMessageSettings(comment, like, star, username);
    }

    @ApiOperation("获取信息通知")
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public String getMessage(HttpServletRequest request, HttpSession session) {
        System.out.println(request.getSession().getAttribute("username"));
        System.out.println(session.getAttribute("username"));
        System.out.println(session.getAttribute("isLogin"));
        System.out.println(Arrays.toString(request.getCookies()));
        return "new";
    }
}
