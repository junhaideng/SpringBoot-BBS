package com.edgar.bbs.service;

import com.edgar.bbs.dao.AvatarDao;
import com.edgar.bbs.domain.Avatar;
import com.edgar.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

@Service
public class AvatarService {
    @Value("${upload.avatar}")  // 获取配置中的文件上传路径
    private String PATH;

    @Value("${bbs.user.default.avatar}")
    private String AVATAR;

    private String basePath = System.getProperty("user.dir");

    @Resource
    private AvatarDao avatarDao;

    public Result getAvatar(HttpServletResponse response, Optional<Avatar> avatar) throws IOException {
        if (!avatar.isPresent()) {
            return new Result(400, "没有对应的文件");
        } else {
            String path = basePath + File.separator + PATH + File.separator + avatar.get().getPath();
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
            return new Result(400, "下载失败");
        }
    }

    public Result uploadAvatar(MultipartFile file, String username) {
        String file_name = file.getOriginalFilename();
        try {
            deleteAvatar(username);
            String BasePath = System.getProperty("user.dir");
            String path = BasePath + PATH + File.separator;
            File f = new File(path);
            if (!f.exists()) {
                f.mkdirs();
            }
            if( file_name!=null&&file_name.contains(".")){
                String ext = file_name.split("\\.")[1];
                file_name = username + "." + ext;
            }else{
                file_name = username;
            }
            file.transferTo(new File(path + file_name));
            avatarDao.updateByUsername( file_name, username);
            return new Result(200, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "上传失败，请重新上传");
        }
    }

    public void deleteAvatar(String username){
        Optional<Avatar> avatar  = avatarDao.getAvatarByUsername(username);

        if(avatar.isPresent()){
            if(avatar.get().getPath().equals(AVATAR )){
                return;
            }
            System.out.println(avatar.get().getPath());
            File f = new File(basePath +  File.separator +PATH+File.separator + avatar.get().getPath());
            System.out.println(f.exists());
            if(f.exists()){
                f.delete();
            }
        }
    }
}
