package com.edgar.bbs.controller;

import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.dao.info.SearchFilesInfo;
import com.edgar.bbs.domain.Article;
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

    @Resource
    private FilesDao filesDao;

    @Resource
    private ArticleDao articleDao;

    @ApiOperation(value = "搜索信息包括文件和文章")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public List<?> getSearchData(@RequestParam(value = "q") String q, @RequestParam(value="type") String type){
        if(type.equals("file")){
            return filesDao.findFileNameContains(q);
        }
        else if(type.equals("article")){
            return articleDao.findArticlesTitleContains(q);
        }else{
            return null;
        }
    }

    @ApiOperation(value = "搜索")
    @RequestMapping(value = "/get", method =  RequestMethod.POST)
    public Map getData(@RequestParam("q") String q){
        return searchService.search(q);
    }
}
