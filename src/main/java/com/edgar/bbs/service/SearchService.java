package com.edgar.bbs.service;


import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.dao.info.SearchArticlesInfo;
import com.edgar.bbs.dao.info.SearchFilesInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchService {
    @Resource
    private ArticleDao articleDao;

    @Resource
    private FilesDao filesDao;

    public Map search(String q){
        List<SearchArticlesInfo> articles = articleDao.findArticlesTitleContains(q);
        List<SearchFilesInfo> files = filesDao.findFileNameContains(q);
        HashMap<String, List<?>> map = new HashMap<>();
        map.put("articles", articles);
        map.put("files", files);
        return map;
    }

//    public Map searchData(String q){
//
//    }
}
