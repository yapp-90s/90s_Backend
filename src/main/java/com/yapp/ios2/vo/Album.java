package com.yapp.ios2.vo;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
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
    @JoinColumn(name ="album_type", referencedColumnName="uid")
    private AlbumType albumType;


    @Column(columnDefinition = "boolean default false")
    private boolean isComplete;

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