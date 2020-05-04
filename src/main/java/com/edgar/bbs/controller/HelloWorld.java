package com.edgar.bbs.controller;

import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.User;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import com.edgar.bbs.utils.IpUtil;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/")
@ApiIgnore
public class HelloWorld {

    @Resource
    private UserDao userDao;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(@RequestParam(value = "name", required = false) String name, HttpServletRequest request){
        System.out.println(name);
        return IpUtil.getIpAddr(request);
    }

    @RequestMapping(value = "/api/getUser", method = RequestMethod.POST)
    public Optional<User> getUser(@RequestParam(value = "username", required = false) String username){
        System.out.println(username);
        System.out.println(userDao.findUserByUsername(username));
        return userDao.findUserByUsername(username);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(@RequestBody Object o){
        System.out.println(o);
        return "hello test";
    }
}
