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
public class Files {
    @Id
    @Column(name = "id", columnDefinition = "bigint auto_increment", nullable = false, unique = true)
    Long id;

    @Column(name = "userId", columnDefinition = "bigint", nullable = false)
    Long userId;

    @Column(name = "fileName", columnDefinition = "varchar(30)", nullable = false)
    String fileName;

    @Column(name = "type", columnDefinition = "varchar(10)", nullable = false)
    String type;

    @Column(name = "downloadTimes", columnDefinition = "bigint")
    Long downloadTimes;

    @Column(name = "uploadTime", columnDefinition = "datetime default now()")
    String uploadTimes;

    @Column(name = "description", columnDefinition = "text")
    String description;

    @Column(name = "path", columnDefinition = "varchar(100)", nullable = false)
    String path;

}
