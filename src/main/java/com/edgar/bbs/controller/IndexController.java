package com.edgar.bbs.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Api(value = "主页")
public class IndexController {
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(){
        return "index";
    }
}
