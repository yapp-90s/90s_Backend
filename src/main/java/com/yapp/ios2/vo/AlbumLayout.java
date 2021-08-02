package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "AlbumLayout")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbumLayout {

    @Id
    @GeneratedValue
    private Long uid;

    @Column(unique = true)
    private Integer code;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer totPaper;

    @Column
    private Integer photoPerPaper;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime releasedAt;

    @Column
    private String path;
}
