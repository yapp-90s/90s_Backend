package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "PhotoInAlbum")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PhotoInAlbum {
    @Id
    @GeneratedValue
    private Long uid;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="album_uid")
    private Album album;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="photo_uid")
    private Photo photo;

    @Column
    private Integer paperNum;

    @Column
    private Integer sequence;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
