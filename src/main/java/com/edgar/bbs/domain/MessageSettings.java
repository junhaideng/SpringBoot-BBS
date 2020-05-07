package com.edgar.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MessageSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false)
    Long id;

    @Column(name = "username", columnDefinition = "varchar(20)")
    String username;

    @Column(name = "`comment`", columnDefinition = "boolean default true")
    boolean comment;  // 评论信息通知

    @Column(name = "`like`", columnDefinition = "boolean default true")
    boolean like;   // 点赞信息通知

    @Column(name = "star", columnDefinition = "boolean default true")
    boolean star;  // 收藏信息通知
}
