package com.yapp.ios2.dto;

import java.time.LocalDateTime;

import javax.print.attribute.standard.DateTimeAtCompleted;

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
    
    private Long filmTypeUid;
    
    private LocalDateTime createAt;
    
    private LocalDateTime printStartAt;
    
    private LocalDateTime printEndAt;

    private LocalDateTime deleteAt;

    public FilmDto(Film film){
        this.filmUid = film.getUid();

        this.name = film.getName();

        this.filmTypeUid = film.getFilmType().getUid();

        this.createAt = film.getCreatedAt();

        this.printStartAt = film.getPrintStartAt();

        this.printEndAt = film.getPrintEndAt();

        this.deleteAt = film.getDeleteAt();
    }

}
