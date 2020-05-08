package com.edgar.bbs.dao.info;

import java.util.Date;

public interface MessageInfo {
    Integer getType();

     String getTitle();

     String getContent();

     Date getTime();

     boolean isRead();
}
