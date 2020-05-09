package com.edgar.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Carousel {
    /**
     * 轮播图
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    Long id;

    @Column(name = "url", columnDefinition = "varchar(100)")
    String url;

    @Column(name = "time", columnDefinition = "datetime default now()")
    Date time;

    @Column(name = "active", columnDefinition = "boolean default true")
    Boolean active;

    @Column(name = "path", columnDefinition = "varchar(200)", nullable = false)
    String path;

    @Column(name = "filename", columnDefinition = "varchar(40)")
    String filename;
}
