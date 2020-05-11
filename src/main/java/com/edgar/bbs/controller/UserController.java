package com.edgar.bbs.controller;

import com.edgar.bbs.dao.*;
import com.edgar.bbs.dao.info.LoginLogInfo;
import com.edgar.bbs.dao.info.MessageInfo;
import com.edgar.bbs.dao.info.MessageSettingsInfo;
import com.edgar.bbs.dao.info.UserInfo;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.service.UserService;
import com.edgar.bbs.utils.Result;
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
import java.util.HashMap;
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

    @Resource
    private MessageDao messageDao;

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
    public Result updateUserInfo(HttpServletRequest request) {
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



    @ApiOperation(value = "修改用户的密码")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result updateUserPasswordByUsername(@RequestParam(value = "username") String username, @RequestParam(value = "oldPassword") String oldPassword, @RequestParam(value = "newPassword") String newPassword) {
        return userService.updatePassword(username, oldPassword, newPassword);
    }

    @ApiOperation(value = "删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result deleteUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password, HttpSession session) {
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
        String username = (String) session.getAttribute("username");
        if(username == null ){
            return new Result(200, "请先登录");
        }
        return userService.postArticle(username, title, type, content);
    }

    @ApiOperation("获取信息设置")
    @RequestMapping(value = "/messageSettings", method = RequestMethod.POST)
    public MessageSettingsInfo getMessageSettingsByUsername(HttpSession session) {
        return userService.getMessageSettingsByUsername((String) session.getAttribute("username"));

    }

    @ApiOperation("修改信息设置")
    @RequestMapping(value = "/changesettings", method = RequestMethod.POST)
    public Result setMessageSettingsByUsername(@RequestBody(required = true) Map<String, Object> map, HttpSession session) {
        Boolean comment = (Boolean) map.get("comment");
        Boolean like = (Boolean) map.get("like");
        Boolean star = (Boolean) map.get("star");
        String username = (String) session.getAttribute("username");
        if(username == null){
            return new Result(400, "请先进行登录");
        }
        return userService.updateMessageSettings(comment, like, star, username);
    }

    @ApiOperation("获取信息通知")
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public List<MessageInfo> getMessage(HttpSession session) {
        return messageDao.getAllByUsername((String) session.getAttribute("username"));
    }

    @ApiOperation("获取未读信息总数")
    @RequestMapping(value = "/message/unread", method = RequestMethod.POST)
    public Map getUnreadMessage(HttpSession session) {
        Long num = messageDao.getUnreadMessage((String) session.getAttribute("username"));
        HashMap<String, Long> map = new HashMap<>();
        map.put("unread", num);
        return map;
    }

    @ApiOperation("删除信息")
    @RequestMapping(value = "/message/delete", method = RequestMethod.POST)
    public Result deleteMessageById(@RequestParam("id") Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if(username == null){
            return new Result(400, "请先进行登录");
        }
        return userService.deleteMessageById(username, id);
    }

    @ApiOperation("已读信息")
    @RequestMapping(value = "/message/read", method = RequestMethod.POST)
    public Result readMessageById(@RequestParam("id") Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if(username == null){
            return new Result(400, "请先进行登录");
        }
        return userService.readMessageById(username, id);
    }

    @ApiOperation("清空全部信息")
    @RequestMapping(value = "/message/clear_all", method = RequestMethod.POST)
    public Result clearAllByUsername(HttpSession session){
        String username = (String) session.getAttribute("username");
        if(username == null){
            return new Result(400, "请先进行登录");
        }
        return userService.clearAllByUsername(username);
    }

    @ApiOperation("全部标记为已读")
    @RequestMapping(value = "/message/read_all", method = RequestMethod.POST)
    public Result readAllByUsername(HttpSession session){
        String username = (String) session.getAttribute("username");
        if(username == null){
            return new Result(400, "请先进行登录");
        }
        return userService.readAllByUsername(username);
    }

}
