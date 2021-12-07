package com.yapp.ios2.dto;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAlbumInDto {

    private String name;

    private Integer coverCode;

    private Integer layoutCode;

}
