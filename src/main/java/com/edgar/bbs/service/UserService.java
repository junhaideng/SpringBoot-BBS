package com.edgar.bbs.service;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.Article;
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

    @Resource
    private ArticleDao articleDao;

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
    public Result signUp(HttpServletRequest request){
        System.out.println(request.getParameterMap());
        try {
            Optional<User> user = userDao.findUserByUsername(request.getParameter("username"));
            if(user.isPresent()){
                return new Result(400, "该用户名已存在");
            }else{
                try{
                    userDao.insertUser(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"), request.getParameter("gender"), request.getParameter("academy"), request.getParameter("grade"));
                    return new Result(200, "用户创建成功, 请登录");
                }catch (Exception e){
                    e.printStackTrace();
                    return new Result(400, "创建用户失败");
                }
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

    /*
    发帖
     */
    public Result postArticle(Long user_id, String title, String type, String content){
        try{
            articleDao.insertArticleByUserId(user_id, title, type, content);
            return new Result(200, "发帖成功");
        } catch (Exception e){
            return new Result(400, "发帖失败");
        }
    }
}
