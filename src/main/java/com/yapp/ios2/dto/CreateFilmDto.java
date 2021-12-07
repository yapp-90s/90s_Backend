package com.yapp.ios2.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateFilmDto {

    private Integer filmCode;

    private String name;

}
