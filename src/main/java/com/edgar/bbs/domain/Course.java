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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint")
    Long id;

    @Column(name = "school", columnDefinition = "varchar(60)")
    String school;

    @Column(name = "teacher", columnDefinition = "varchar(30)")
    String teacher;

    @Column(name = "courseName", columnDefinition = "varchar(40)")
    String courseName;


}
