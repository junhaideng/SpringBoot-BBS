package com.edgar.bbs.service;

import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.utils.FileUtil;
import com.edgar.bbs.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Optional;

/**
 * 文件
 */
@Service
public class FileService {
    @Value("${upload.files}")  // 获取配置中的文件上传路径
    private String PATH;

    @Resource
    private FilesDao filesDao;


    public Result getFile(HttpServletResponse response, Long id) throws IOException {
        Optional<Files> file = filesDao.findById(id);
        String basePath = System.getProperty("user.dir");
        if (!file.isPresent()) {
            return new Result(400, "没有对应的文件");
        } else {
            Long downloadTimes = file.get().getDownloadTimes() + 1;
            file.get().setDownloadTimes(downloadTimes);
            filesDao.saveAndFlush(file.get());
            String path = basePath + File.separator + PATH + File.separator + file.get().getPath();
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
                    return new Result(200, "文件传送完成");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    bis.close();
                    fis.close();
                }
            }
            return new Result(400, "文件传输失败");
        }
    }

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
            filesDao.insertFile(description, map.get("origin"), name_to_store, type, username);
            return new Result(200, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "上传失败，请重新上传");

        }
    }

}
