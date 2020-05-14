package com.edgar.bbs.dao.info;


public interface MessageInfo {
    Long getId();

     String getType();

     String getTitle();

     String getContent();

     String getTime();

     Boolean getRead();

     String getUrl();
}
