package com.edgar.bbs.dao.info;

public interface SearchFilesInfo {
    /*
  文件自定义查询结果集
   */
    String getFile_name();

    Long getId();

    String getType();  // 前端设置为 文件

    String getDescription();

    String getPath();

    String getUsername();

    String getDownload_times();
}
