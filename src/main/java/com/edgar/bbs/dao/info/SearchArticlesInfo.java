package com.edgar.bbs.dao.info;

public interface SearchArticlesInfo {
    /*
    文章自定义查询结果集
     */
    String getTitle();

    Long getId();

    String getType();  // 前端设置

    String getContent();
}
