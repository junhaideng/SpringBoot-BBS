package com.edgar.bbs.service;

import com.edgar.bbs.dao.CarouselDao;
import com.edgar.bbs.domain.Carousel;
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

@Service
public class CarouselService {
    @Resource
    private CarouselDao carouselDao;

    @Value("${upload.carousel}")  // 获取配置中的文件上传路径
    private String PATH;

    public Result getCarousel(HttpServletResponse response, Optional<Carousel> carousel) throws IOException {
        String basePath = System.getProperty("user.dir");
        if (!carousel.isPresent()) {
            return new Result(400, "没有对应的文件");
        } else {
            String path = basePath + File.separator + carousel.get().getPath();
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

    public Result uploadCarousel(MultipartFile file) throws IOException {
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
            carouselDao.insertCarousel(true, PATH + File.separator + name_to_store, file_name);
            return new Result(200, "上传成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(400, "上传失败，请重新上传");
        }
    }
}

