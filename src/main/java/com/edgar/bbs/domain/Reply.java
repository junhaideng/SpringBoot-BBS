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
public class Reply {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false, unique = true)
    Long id;

    @Column(name = "article_id", columnDefinition = "bigint")
    Long articleId;

    @Column(name = "username", columnDefinition = "varchar(40)")
    String username;

    @Column(name = "reply", columnDefinition = "text")
    String reply;

    @Column(name = "`like`", columnDefinition = "bigint default 0")
    Long like;

    @Column(name = "dislike", columnDefinition = "bigint default 0")
    Long dislike;

    @Column(name = "comments", columnDefinition = "bigint default 0")
    Long comments;
}
