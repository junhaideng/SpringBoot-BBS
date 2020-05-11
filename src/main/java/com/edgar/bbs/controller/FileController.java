package com.edgar.bbs.controller;

import com.edgar.bbs.service.UserService;
import com.edgar.bbs.utils.RedisUtils;
import com.edgar.bbs.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@Api("文件相关")
@RequestMapping("/api/file")
public class FileController {
    @Resource
    private UserService userService;


    @ApiOperation(value = "下载文件")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public Result downloadFile(@Param(value = "file_id") Long file_id, HttpServletResponse response) throws IOException {
        return userService.downloadFile(response, file_id);
    }
}
