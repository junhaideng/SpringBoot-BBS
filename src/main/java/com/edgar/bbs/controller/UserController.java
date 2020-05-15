package com.edgar.bbs.controller;

import com.edgar.bbs.dao.*;
import com.edgar.bbs.dao.info.LoginLogInfo;
import com.edgar.bbs.dao.info.MessageInfo;
import com.edgar.bbs.dao.info.MessageSettingsInfo;
import com.edgar.bbs.dao.info.UserInfo;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.Avatar;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.service.AvatarService;
import com.edgar.bbs.service.FileService;
import com.edgar.bbs.service.UserService;
import com.edgar.bbs.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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

    @Resource
    private AvatarService avatarService;

    @Resource
    private AvatarDao avatarDao;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public Result login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpServletRequest request) throws IOException {
        return userService.login(username, password, request);
    }


    @ApiOperation(value = "获取用户登录日志")
    @RequestMapping(value = "/loginlog", method = RequestMethod.POST)
    public List<LoginLogInfo> getUserLoginLog(HttpSession session) {
        String username = session.getAttribute("username").toString();
        if(username ==null){
            return null;
        }
        return loginLogDao.findAllByUsername(username);
    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public UserInfo getUserInfo(HttpSession session) {
        String username = session.getAttribute("username").toString();
        if(username ==null){
            return null;
        }
        UserInfo user = userDao.getInfoUsingUsername(username);
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
        String username = session.getAttribute("username").toString();
        if(username ==null) {
            return null;
        }
        return articleDao.findArticlesByUsername(username);
    }

    @ApiOperation(value = "获取用户的文件信息")
    @RequestMapping(value = "/files", method = RequestMethod.POST)
    public List<Files> getUserFiles(HttpSession session) {
        String username = session.getAttribute("username").toString();
        if(username ==null){
            return null;
        }
        return filesDao.findAllByUsername(username);
    }

    @ApiOperation(value = "删除文件")
    @RequestMapping(value = "/delfiles", method = RequestMethod.POST)
    public Result delFiles(@RequestBody Long[] filesId, HttpSession session) {
        String username = session.getAttribute("username").toString();
        if(username ==null){
            return new Result(400, "请先进行登录");
        }
        return userService.deleteFilesByIdAndUsername(filesId, username);

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
        if (username == null) {
            return new Result(400, "请先登录");
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
        if (username == null) {
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
        if (username == null) {
            return new Result(400, "请先进行登录");
        }
        return userService.deleteMessageById(username, id);
    }

    @ApiOperation("已读信息")
    @RequestMapping(value = "/message/read", method = RequestMethod.POST)
    public Result readMessageById(@RequestParam("id") Long id, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new Result(400, "请先进行登录");
        }
        return userService.readMessageById(username, id);
    }

    @ApiOperation("清空全部信息")
    @RequestMapping(value = "/message/clear_all", method = RequestMethod.POST)
    public Result clearAllByUsername(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new Result(400, "请先进行登录");
        }
        return userService.clearAllByUsername(username);
    }

    @ApiOperation("全部标记为已读")
    @RequestMapping(value = "/message/read_all", method = RequestMethod.POST)
    public Result readAllByUsername(HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return new Result(400, "请先进行登录");
        }
        return userService.readAllByUsername(username);
    }

    @ApiOperation("获取对应的头像")
    @RequestMapping("/avatar/get")
    public Result getAvatar(HttpSession session, HttpServletResponse response) throws IOException {
        String username = (String) session.getAttribute("username");
        if(username==null){
            return new Result(400, "未登录无法获取用户头像");
        }else{
            Optional<Avatar> avatar = avatarDao.getAvatarByUsername(username);
             return avatarService.getAvatar(response,avatar);
        }
    }

    @ApiOperation("通过用户名获取头像")
    @RequestMapping("/avatar/get_by_username")
    public Result getAvatarByUsername(@RequestParam("username") String username, HttpServletResponse response) throws IOException {
        Optional<Avatar> avatar = avatarDao.getAvatarByUsername(username);
        return avatarService.getAvatar(response,avatar);
    }

    @ApiOperation("上传头像")
    @RequestMapping(value = "/avatar/upload", method = RequestMethod.POST)
    public Result uploadAvatar(@RequestParam("file") MultipartFile file, HttpSession session){
        String username = (String) session.getAttribute("username");
        if(username == null){
            return new Result(400, "请登录");
        }
        return avatarService.uploadAvatar(file, username);
    }
}
