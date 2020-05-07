package com.edgar.bbs.service;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.dao.MessageSettingsDao;
import com.edgar.bbs.dao.UserDao;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.domain.User;
import com.edgar.bbs.utils.FileUtil;
import com.edgar.bbs.utils.MessageSettingsInfo;
import com.edgar.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
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

    @Resource
    private MessageSettingsDao messageSettingsDao;

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

    public Result uploadFile(MultipartFile file, String type, String description, String username) throws IOException {
        String file_name = file.getOriginalFilename();
        if (file_name == null) {
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
            String name_to_store = map.get("name") + "." + map.get("suffix");
            file.transferTo(new File(path + name_to_store));
            filesDao.insertFile(description, map.get("origin"), PATH + File.separator + name_to_store, type, username);
            return new Result(200, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "上传失败，请重新上传");

        }
    }

    /*
    下载文件
     */
    public Result downloadFile(HttpServletResponse response, Long id) throws IOException {
        String basePath = System.getProperty("user.dir");
        Optional<Files> file = filesDao.findById(id);
        if (!file.isPresent()) {
            return new Result(400, "没有对应的文件");
        } else {
            String path = basePath + File.separator + file.get().getPath();
            File f = new File(path);
            if (f.exists()) {
                byte[] b = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis);
                try {
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(b);
                    while (i != -1) {
                        os.write(b, 0, i);
                        i = bis.read(b);
                    }
                    return new Result(200, "下载完成");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    bis.close();
                    fis.close();
                }
            }
            return new Result(200, "下载失败");
        }


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
        }catch (Exception e){
            return new Result(400, "修改失败, 请重新修改");
        }
    }
}
