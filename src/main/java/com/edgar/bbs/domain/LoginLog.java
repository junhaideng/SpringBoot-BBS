package com.edgar.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LoginLog")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginLog {
    @Id
    @Column(name = "id", columnDefinition = "bigint auto_increment", nullable = false, unique = true)
    Long id;

    @Column(name = "userId", columnDefinition = "bigint")
    Long userId;

   @Column(name = "ip", columnDefinition = "varchar(20)", nullable = false)
    String ip;

   @Column(name = "address", columnDefinition = "varchar(50)")
    String address;

   @Column(name = "create_time", columnDefinition = "datetime default now()")
    String create_time;

}
