package com.yapp.ios2.dto;

import java.time.LocalDateTime;

import javax.print.attribute.standard.DateTimeAtCompleted;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilmDto {

    private Integer filmUid;

    private String name;
    
    private Integer filmTypeUid;
    
    private LocalDateTime createAt;
    
    private LocalDateTime printStartAt;
    
    private LocalDateTime printEndAt;

    private LocalDateTime deleteAt;

}
