package com.edgar.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

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
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    Long id;

    @Column(name = "userId", columnDefinition = "bigint")
    Long userId;

    @Column(name = "title", columnDefinition = "varchar(200)")
    String title;

    @Column(name = "content", columnDefinition = "text")
    String content;


    @Column(name = "create_time", columnDefinition = "datetime default now()")
    String createTime;

    @Column(name = "type", columnDefinition = "varchar(40)")
    String type;

    @Column(name = "`read`", columnDefinition = "bigint default 0")
    Long read;

    @Column(name = "star", columnDefinition = "bigint default 0")
    Integer star;

    @Column(name = "comments", columnDefinition = "bigint default 0")
    Integer comments;
}
