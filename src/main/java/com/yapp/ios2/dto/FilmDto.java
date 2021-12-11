package com.yapp.ios2.dto;

import java.time.LocalDateTime;

import com.yapp.ios2.vo.Film;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {

    private Long filmUid;

    private String name;
    
    private Integer filmCode;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime printStartedAt;
    
    private LocalDateTime printEndedAt;

    private LocalDateTime deletedAt;

    public FilmDto(Film film){
        this.filmUid = film.getUid();

        this.name = film.getName();

        this.filmCode = film.getFilmType().getCode();

        this.createdAt = film.getCreatedAt();

        this.printStartedAt = film.getPrintStartAt();

        this.printEndedAt = film.getPrintEndAt();

        this.deletedAt = film.getDeleteAt();
    }

}
