package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

enum PhotoType{
    ORIGINAL,
    DEVELOPED,
    DECORATED
}

@Builder
@Entity
@Table(name = "photo")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Photo {

    @Id
    @GeneratedValue
    private Long uid;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="film_uid")
    private Film film;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="album_uid")
    private Album album;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
