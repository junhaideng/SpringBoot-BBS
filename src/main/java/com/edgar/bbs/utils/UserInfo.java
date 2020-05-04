package com.edgar.bbs.utils;

public interface UserInfo {
    /*
    自定义查询结果集 获取用户的相关信息
     */
    Long getId();

    String getUsername();

    String getAcademy();

    String getAvatar();

    String getEmail();

    String getSex();

    String getCreate_time();

    String getGrade();

    Integer getAge();

}
