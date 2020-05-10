package com.edgar.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseComments {
    /*
    学生对于课程的评价
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    Long id;

    @Column(name = "course_id", columnDefinition = "bigint")
    Long course_id;

    @Column(name = "username", columnDefinition = "varchar(40)")
    String username;

    @Column(name = "comment", columnDefinition = "text")
    String comment;
}
