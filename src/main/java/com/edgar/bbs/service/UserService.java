package com.edgar.bbs.service;

import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.User;
import com.edgar.bbs.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class UserService {
    /*
    用户相关的操作业务
     */
    @Resource
    private UserDao userDao;

    public Result login(String username, String password, HttpServletRequest request) {
        Optional<User> user = userDao.findUserByUsername(username);
        if (user.isPresent()) {
            String realPassword = user.get().getPassword();
            if (realPassword.equals(password)) {
                // 登录成功
                HttpSession session = request.getSession();
                session.setAttribute("isLogin", true);
                session.setAttribute("username", username);
                return new Result(200, "登录成功");

            } else {
                return new Result(300, "密码错误");
            }
        } else {
            return new Result(400, "无该用户");
        }
    }

    /*
    用户注册
     */
    public Result signUp(String username, String password){
        try {
            Optional<User> user = userDao.findUserByUsername(username);
            if(user.isPresent()){
                return new Result(400, "该用户名已存在");
            }else{
                userDao.insertUser(username, password);
                return new Result(200, "用户创建成功");
            }

        } catch (Exception e){
            e.printStackTrace();
            return new Result(400, "用户创建失败");
        }


    }

    /*
    修改密码
     */
    public Result updatePassword(String username,String oldPassword,  String  newPassword){
        Optional<User> user = userDao.findUserByUsername(username);
        if(user.isPresent()){
            if(user.get().getPassword().equals(oldPassword)){
                userDao.updatePasswordByUsername(username, newPassword);
                return new Result(200, "修改成功");
            }else{
                return new Result(400, "旧密码输入错误");
            }
        }else{
            return new Result(400, "查找不到对应的用户");
        }
    }

    /*
    用户的注销
     */
    public Result deleteUser(String username, String password){
        Optional<User> user = userDao.findUserByUsername(username);
        if(user.isPresent()){
            if(user.get().getPassword().equals(password)){
                userDao.deleteUserByUsername(username);
                return new Result(200, "用户注销成功");
            }else{
                return new Result(400, "密码输入错误");
            }
        }else{
            return new Result(400, "无此用户");
        }
    }
}
