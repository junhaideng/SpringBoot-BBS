package com.edgar.bbs.service;

import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.domain.Files;
import com.edgar.bbs.utils.RedisUtils;
import com.edgar.bbs.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

/**
 * 向外提供文件
 */
@Service
public class FileService {
    @Resource
    private RedisUtils redisUtils;

    @Resource
    private FilesDao filesDao;


    public Result transferFile(HttpServletResponse response, Optional<Files> file, Long id) throws IOException {

        String basePath = System.getProperty("user.dir");
        if (!file.isPresent()) {
            return new Result(400, "没有对应的文件");
        } else {
            redisUtils.zincrby("file",id.toString(), 1.0);
            Long downloadTimes = file.get().getDownloadTimes()+1;
            file.get().setDownloadTimes(downloadTimes);
            filesDao.saveAndFlush(file.get());

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
                    return new Result(200, "文件传送完成");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    bis.close();
                    fis.close();
                }
            }
            return new Result(200, "文件传输失败");
        }
    }

}
