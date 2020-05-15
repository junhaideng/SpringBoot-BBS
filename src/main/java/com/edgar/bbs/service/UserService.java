package com.edgar.bbs.service;

import com.alibaba.fastjson.JSONObject;
import com.edgar.bbs.dao.*;
import com.edgar.bbs.dao.info.MessageSettingsInfo;
import com.edgar.bbs.domain.Article;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.domain.Message;
import com.edgar.bbs.domain.User;
import com.edgar.bbs.utils.IpUtil;
import com.edgar.bbs.utils.LocationUtil;
import com.edgar.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
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

    @Resource
    private FilesDao filesDao;

    @Resource
    private MessageSettingsDao messageSettingsDao;

    @Resource
    private MessageDao messageDao;

    @Resource
    private AvatarDao avatarDao;

    @Resource
    private LoginLogDao loginLogDao;

    @Value("${upload.files}")
    private String PATH;


    public Result login(String username, String password, HttpServletRequest request) throws IOException {
        Optional<User> user = userDao.findUserByUsername(username);
        if (user.isPresent()) {
            String realPassword = user.get().getPassword();
            if (realPassword.equals(password)) {
                // 登录成功
                HttpSession session = request.getSession();
                session.setAttribute("isLogin", true);
                session.setAttribute("username", username);
                String ip = IpUtil.getIpAddr(request);
                JSONObject jsonObject = LocationUtil.getLocation(ip);
                String address = "";
                if(jsonObject.getIntValue("status")==0){
                     address = "unknown";
                }else{
                    address = jsonObject.getJSONObject("content").getString("address");
                }
                loginLogDao.insert(address, ip, username);
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
    public Result signUp(HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            Optional<User> user = userDao.findUserByUsername(username);
            if (user.isPresent()) {
                return new Result(400, "该用户名已存在");
            } else {
                try {
                    messageSettingsDao.insert(request.getParameter("username"));
                    userDao.insertUser(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"), request.getParameter("gender"), request.getParameter("academy"), request.getParameter("grade"));
                    avatarDao.insert(username);
                    return new Result(200, "用户创建成功, 请登录");
                } catch (Exception e) {
                    e.printStackTrace();
                    return new Result(400, "创建用户失败");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "用户创建失败");
        }


    }

    /*
    修改密码
     */
    public Result updatePassword(String username, String oldPassword, String newPassword) {
        Optional<User> user = userDao.findUserByUsername(username);
        if (user.isPresent()) {
            if (user.get().getPassword().equals(oldPassword)) {
                userDao.updatePasswordByUsername(username, newPassword);
                return new Result(200, "修改成功");
            } else {
                return new Result(400, "旧密码输入错误");
            }
        } else {
            return new Result(400, "查找不到对应的用户");
        }
    }

    /*
    用户的注销
     */
    public Result deleteUser(String username, String password, String real) {
        if (username.equals(real)) {
            Optional<User> user = userDao.findUserByUsername(username);
            if (user.isPresent()) {
                if (user.get().getPassword().equals(password)) {
                    userDao.deleteUserByUsername(username);
                    return new Result(200, "用户注销成功");
                } else {
                    return new Result(400, "密码输入错误");
                }
            } else {
                return new Result(400, "无此用户");
            }
        } else {
            return new Result(400, "当前登录用户与输入用户不一致");
        }
    }

    /*
    发帖
     */
    public Result postArticle(String username, String title, String type, String content) {
        try {
            articleDao.insertArticleByUsername(username, title, type, content);
            return new Result(200, "发帖成功");
        } catch (Exception e) {
            return new Result(400, "发帖失败");
        }
    }

    /*
    删除文件
     */
    public Result deleteFilesByIdAndUsername(Long[] filesId, String username) {
        try {
            for (Long id : filesId) {
                Optional<Files> file = filesDao.findByIdAndUsername(id, username);
                if (file.isPresent()) {
                    String BasePath = System.getProperty("user.dir");
                    System.out.println(BasePath  + PATH + File.separator + file.get().getPath());
                    new File(BasePath  + PATH + File.separator + file.get().getPath()).delete();
                    filesDao.deleteById(id);
                } else {
                    return new Result(400, "文件不存在");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "删除失败");
        }
        return new Result(200, "删除成功");
    }

    /*
    通知修改
     */
    public Result updateMessageSettings(boolean comment, boolean like, boolean star, String username) {
        try {
            messageSettingsDao.updateMessageSettingsByUsername(comment, like, star, username);
            return new Result(200, "修改成功");
        } catch (Exception e) {
            return new Result(400, "修改失败，请重新修改");
        }
    }

    /*
    获取通知设置
     */
    public MessageSettingsInfo getMessageSettingsByUsername(String username) {
        return messageSettingsDao.findMessageSettingsUsingUsername(username);
    }

    public Result setMessageSettingsByUsername(boolean comment, boolean like, boolean star, String username) {
        try {
            messageSettingsDao.updateMessageSettingsByUsername(comment, like, star, username);
            return new Result(200, "保存成功");
        } catch (Exception e) {
            return new Result(400, "修改失败，请重新修改");
        }
    }

    /**
     * 修改用户信息
     */
    public Result updateUserInfo(HttpServletRequest request) {
        try {
            String username = (String) request.getSession().getAttribute("username");
            String academy = request.getParameter("academy");
            String gender = request.getParameter("gender");
            Integer age = Integer.parseInt(request.getParameter("age"));
            String grade = request.getParameter("grade");
            String email = request.getParameter("email");
            String description = request.getParameter("description");
            String avatar = request.getParameter("avatar");
            userDao.updateInfo(username, academy, gender, age, grade, email, description, avatar);
            return new Result(200, "修改成功");
        } catch (Exception e) {
            return new Result(400, "修改失败, 请重新修改");
        }
    }

    /**
     * 按照id删除信息
     */
    public Result deleteMessageById(String username, Long id) {
        Optional<Message> message = messageDao.findMessageByUsernameAndId(username, id);
        if (message.isPresent()) {
            try {
                messageDao.deleteMessageById(id);
                return new Result(200, "删除成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(400, "删除失败");
            }
        } else {
            // 该通知并不属于该用户
            return new Result(400, "权限错误");
        }
    }

    /**
     * 按照id标记信息为已读
     */
    public Result readMessageById(String username, Long id) {
        Optional<Message> message = messageDao.findMessageByUsernameAndId(username, id);
        if (message.isPresent()) {
            try {
                messageDao.readMessageById(id);
                return new Result(200, "标记为已读成功");
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(400, "标记失败");
            }
        } else {
            // 该通知并不属于该用户
            return new Result(400, "权限错误");
        }
    }

    public Result clearAllByUsername(String username) {
        try {
            messageDao.deleteMessageByUsername(username);
            return new Result(200, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "删除失败");
        }
    }

    public Result readAllByUsername(String username) {
        try {
            messageDao.updateByUsername(username);
            return new Result(200, "标记成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "标记失败");
        }
    }

    /*
    删帖子
     */

    public Result deleteArticleById(Long id, HttpSession session){
        String username = session.getAttribute("username").toString();
        if(username==null){
            return new Result(400, "用户不存在");
        }
        Optional<Article> article = articleDao.findById(id);
        if(!article.isPresent()){
            return new Result(400, "该帖子不存在");
        }
        else{
            if(article.get().getUsername().equals(username)){
                articleDao.delete(article.get());
                return  new Result(200, "删除成功");
            }else{
                return new Result(400, "没有权限");
            }
        }
    }


}
