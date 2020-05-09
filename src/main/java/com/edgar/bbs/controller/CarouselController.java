package com.edgar.bbs.controller;

import com.edgar.bbs.dao.CarouselDao;
import com.edgar.bbs.dao.info.CarouselInfo;
import com.edgar.bbs.domain.Carousel;
import com.edgar.bbs.service.CarouselService;
import com.edgar.bbs.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/carousel")
@Api("轮播图")
public class CarouselController {
    @Resource
    private CarouselDao carouselDao;

    @Resource
    private CarouselService carouselService;

    @ApiOperation("获取轮播图")
    @PostMapping("/get")
    public List<CarouselInfo> getCarousel(@RequestParam("num") Integer num) {
        return carouselDao.findAllByActive(1, num);
    }

    @ApiOperation("上传轮播图")
    @PostMapping("/upload")
    public Result uploadCarousel(MultipartFile file) throws IOException {
        return carouselService.uploadCarousel(file);
    }

    @ApiOperation("轮播图文件")
    @GetMapping("/get")
    public Result getCarousel(@RequestParam("id") Long id, HttpServletResponse response) throws IOException {
        Optional<Carousel> carousel = carouselDao.findById(id);
        return carouselService.getCarousel(response, carousel);
    }
}
