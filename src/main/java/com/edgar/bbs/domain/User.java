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
public class User {
    /*
    用户数据库
     */
    @Id
    @Column(name = "id", columnDefinition = "bigint auto_increment", nullable = false, unique = true)
    Long id;

    @Column(name = "username", columnDefinition = "varchar(20) not null", unique = true)
    String username;

    @Column(name = "password", columnDefinition = "varchar(20)", nullable = false)
    String password;

    @Column(name = "avatar", columnDefinition = "varchar(100)")
    String avatar;

    @Column(name = "email", columnDefinition = "varchar(30)")
    String email;

    @Column(name = "academy", columnDefinition = "varchar(30)")
    String academy;

    @Column(name = "sex", columnDefinition = "varchar(30)")
    String sex; // 男   女   保密

    @Column(name = "age", columnDefinition = "smallint")
    Integer age;

    @Column(name = "grade", columnDefinition = "varchar(30)")
    String grade;

    @Column(name = "create_time", columnDefinition = "datetime default now()")
    String create_time;

}
