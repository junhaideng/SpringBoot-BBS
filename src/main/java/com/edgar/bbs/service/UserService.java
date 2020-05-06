package com.edgar.bbs.service;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.domain.User;
import com.edgar.bbs.utils.FileUtil;
import com.edgar.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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

    @Value("${upload.files}")  // 获取配置中的文件上传路径
    private String PATH;

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
    public Result signUp(HttpServletRequest request) {
        System.out.println(request.getParameterMap());
        try {
            Optional<User> user = userDao.findUserByUsername(request.getParameter("username"));
            if (user.isPresent()) {
                return new Result(400, "该用户名已存在");
            } else {
                try {
                    userDao.insertUser(request.getParameter("username"), request.getParameter("password"), request.getParameter("email"), request.getParameter("gender"), request.getParameter("academy"), request.getParameter("grade"));
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
    public Result deleteUser(String username, String password) {
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
    }

    /*
    发帖
     */
    public Result postArticle(Long user_id, String title, String type, String content) {
        try {
            articleDao.insertArticleByUserId(user_id, title, type, content);
            return new Result(200, "发帖成功");
        } catch (Exception e) {
            return new Result(400, "发帖失败");
        }
    }

    /*
    删除文件
     */
    public Result deleteFilesById(Long[] filesId) {
        try {
            for (Long id : filesId) {
                Optional<Files> file = filesDao.findById(id);
                if (file.isPresent()) {
                    String BasePath = System.getProperty("user.dir");
                    new File(BasePath + File.separator + file.get().getPath()).delete();
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
    上传文件
     */

    public Result uploadFile(MultipartFile file, String type, String description, Long user_id) throws IOException {
        String file_name = file.getOriginalFilename();
        if(file_name == null){
            file_name = "未命名";
        }
        try {
            String BasePath = System.getProperty("user.dir");
            String path = BasePath + PATH + File.separator;
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            HashMap<String, String> map = FileUtil.dealWithFileName(file_name);
            String name_to_store = map.get("name") +"."+ map.get("suffix");
            file.transferTo(new File(path + name_to_store));
            filesDao.insertFile(description, map.get("origin") , PATH + File.separator + name_to_store, type, user_id);
            return new Result(200, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "上传失败，请重新上传");

        }
    }

}
