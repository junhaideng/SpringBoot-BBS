package com.edgar.bbs.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class JwcNotice {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false, unique = true)
    Long id;

    @Column(name = "title", columnDefinition = "varchar(100)")
    String title;

    @Column(name = "link", columnDefinition = "varchar(100)")
    String link;

    @Column(name = "pubDate", columnDefinition = "varchar(30)")
    String pubDate;
}
