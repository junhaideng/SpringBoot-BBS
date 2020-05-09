package com.edgar.bbs.dao.info;

import java.util.Date;

public interface MessageInfo {
    Long getId();

     String getType();

     String getTitle();

     String getContent();

     String getTime();

     Boolean getRead();
}
