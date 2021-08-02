package com.yapp.ios2.vo;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "Album")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    @Id
    @GeneratedValue
    private Long uid;

    @Column(length = 45, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name ="album_cover", referencedColumnName="uid")
    private AlbumCover albumCover;

    @ManyToOne
    @JoinColumn(name ="album_layout", referencedColumnName="uid")
    private AlbumLayout albumLayout;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime completedAt;

    public Album(String name) {
        this.name = name;
    }
}
