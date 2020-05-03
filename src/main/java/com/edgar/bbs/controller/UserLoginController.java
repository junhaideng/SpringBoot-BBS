package com.edgar.bbs.controller;

import com.edgar.bbs.service.UserLoginService;
import com.edgar.bbs.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/api/user")
@Api(value = "用户接口")
public class UserLoginController {
    @Resource
    private UserLoginService userLoginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "用户登录", notes = "用户登录")
    public Result login(@RequestParam(value = "username", required = false) String username, @RequestParam(value = "password", required = false) String password, HttpServletRequest request){
        return  userLoginService.login(username, password, request);
    }

}
