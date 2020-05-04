package com.edgar.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    /*
    用户的帖子
     */
    @Id
    @Column(name = "id", columnDefinition = "bigint auto_increment")
    Long id;

    @Column(name = "userId", columnDefinition = "bigint")
    Long userId;

    @Column(name = "title", columnDefinition = "varchar(20)")
    String title;

    @Column(name = "content", columnDefinition = "text")
    String content;


    @Column(name = "create_time", columnDefinition = "datetime default now()")
    String createTime;

//    @Column(name = "read", columnDefinition = "bigint")
//    Long read;
//
//    @Column(name = "star", columnDefinition = "bigint")
//    Integer star;
//
//    @Column(name = "like", columnDefinition = "bigint")
//    Integer like;
//
//    @Column(name = "comments", columnDefinition = "bigint")
//    Integer comments;
}
