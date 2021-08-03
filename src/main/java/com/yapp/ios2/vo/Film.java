package com.yapp.ios2.vo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "Film")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Id
    @GeneratedValue
    private Long uid;

    @Column(length = 45, nullable = false)
    private String name;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="filmType_uid")
    private FilmType filmType;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name ="user_uid")
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime printStartAt;

    @Column
    private LocalDateTime printEndAt;

    @Column
    private LocalDateTime deleteAt;
}
