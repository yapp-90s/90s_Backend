package com.yapp.ios2.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StartPrintingDto {
    private Integer film_uid;
    private Integer photo_uid;
    private Integer film_code;
}
