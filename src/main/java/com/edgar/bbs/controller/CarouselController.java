package com.edgar.bbs.controller;

import com.edgar.bbs.dao.CarouselDao;
import com.edgar.bbs.dao.info.CarouselInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/api/carousel")
@Api("轮播图")
public class CarouselController {
    @Resource
    private CarouselDao carouselDao;

    @ApiOperation("获取轮播图")
    @PostMapping("/get")
    List<CarouselInfo> getCarousel(@RequestParam("num") Integer num) {
        return carouselDao.findAllByActive(1, num);
    }
}
