package com.edgar.bbs.controller;

import com.edgar.bbs.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "搜索接口")
@RequestMapping(value = "/api/search")
public class SearchController {
    @Resource
    private SearchService searchService;

    @ApiOperation(value = "搜索信息包括文件和文章")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Map getSearchData(@RequestParam(value = "q") String q){
        return searchService.search(q);
    }
}
