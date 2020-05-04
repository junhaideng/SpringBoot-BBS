package com.edgar.bbs.controller;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.LoginLogDao;
import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.LoginLog;
import com.edgar.bbs.service.UserLoginService;
import com.edgar.bbs.utils.Result;
import com.edgar.bbs.utils.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Api(value = "登录日志")
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private LoginLogDao loginLogDao;

    @Resource
    private UserLoginService userLoginService;

    @Resource
    private UserDao userDao;

    @Resource
    private ArticleDao articleDao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public Result login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpServletRequest request){
        return  userLoginService.login(username, password, request);
    }


    @ApiOperation(value = "获取用户登录日志")
    @RequestMapping(value = "/loginlog", method = RequestMethod.POST)
    public List<LoginLog> getUserLoginLog(@RequestParam(value = "user_id") Long user_id){
        return loginLogDao.findAllByUserId(user_id);
    }

    @ApiOperation(value = "获取用户信息")
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public UserInfo getUserInfo(@RequestParam(value = "username") String username)
    {
        UserInfo user = userDao.getInfoByUsername(username);
        System.out.println(user);
        return user;
    }

    @ApiOperation(value = "获取用户的帖子")
    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public List<Article> getUserArticles(@RequestParam(value = "userId") Long userId){
        return articleDao.findArticlesByUserId(userId);
    }

}