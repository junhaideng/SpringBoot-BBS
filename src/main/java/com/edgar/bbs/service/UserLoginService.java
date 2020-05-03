package com.edgar.bbs.service;

import com.alibaba.fastjson.JSON;
import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.User;
import com.edgar.bbs.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserLoginService {
    @Resource
    private UserDao userDao;

    public Result login(String username, String password, HttpServletRequest request) {
        List<User> userList = userDao.findAllByUsername(username);
        if (userList.size() > 0) {
            String realPassword = userDao.findAllByUsername(username).get(0).getPassword();
            if (realPassword.equals(password)) {
                // 登录成功
                HttpSession session = request.getSession();
                session.setAttribute("isLogin", true);
                session.setAttribute("username", username);
                return new Result(200, "登录成功", JSON.parse("{}"));

            } else {
                return new Result(300, "密码错误", JSON.parse("{}"));
            }
        } else {
            return new Result(400, "无该用户", JSON.parse("{}"));
        }
    }
}
