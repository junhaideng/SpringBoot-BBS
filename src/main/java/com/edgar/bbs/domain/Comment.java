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
public class Comment {
    /*
    回答下的回复
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    Long id;

    @Column(name = "reply_id", columnDefinition = "bigint")
    Long reply_id;  // 回复的回答

    @Column(name = "comment", columnDefinition = "text")
    String comment;

    @Column(name = "username", columnDefinition = "varchar(30)")
    String username;  // 哪个用户回复的
}
