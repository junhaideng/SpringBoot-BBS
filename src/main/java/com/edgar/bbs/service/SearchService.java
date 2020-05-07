package com.edgar.bbs.service;


import com.edgar.bbs.dao.ArticleDao;
import com.edgar.bbs.dao.FilesDao;
import com.edgar.bbs.utils.SearchArticlesInfo;
import com.edgar.bbs.utils.SearchFilesInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

        System.out.println(files);
        return map;
    }
}
