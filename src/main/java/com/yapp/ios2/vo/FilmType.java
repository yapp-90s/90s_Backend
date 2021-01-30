package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "FilmType")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FilmType {
    @Id
    @GeneratedValue
    private Long uid;

    @Column(length = 45, nullable = true)
    private String name;

    @Column(length = 999, nullable = true)
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    private LocalDateTime releasedAt;

}
