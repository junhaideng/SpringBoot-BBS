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
public class User {
    /*
    用户数据库
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false, unique = true)
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

    @Column(name = "gender", columnDefinition = "varchar(30)")
    String gender; // 男   女   保密

    @Column(name = "age", columnDefinition = "smallint")
    Integer age;

    @Column(name = "grade", columnDefinition = "varchar(30)")
    String grade;

    @Column(name = "create_time", columnDefinition = "datetime default now()")
    String create_time;

    @Column(name = "description", columnDefinition = "text")
    String description;
}
