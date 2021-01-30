package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "cover")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AlbumType {

    @Id
    private Long uid;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer photoPerPaper;

    @Column
    private Integer totalPaper;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime releasedAt;

    @Column
    private String path;
}
