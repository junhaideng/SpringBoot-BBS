package com.edgar.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Feedback {
    /*
    反馈信息
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    Long id;

    @Column(name = "username", columnDefinition = "varchar(30)", nullable = false)
    String username;

    @Column(name = "content", columnDefinition = "text", nullable = false)
    String content;

    @Column(name = "title", columnDefinition = "varchar(200)")
    String title;

    @Column(name = "email", columnDefinition = "varchar(30)")
    String email;

    @Column(name = "active", columnDefinition = "boolean default true")
    Boolean active;


}
