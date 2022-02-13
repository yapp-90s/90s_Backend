package com.yapp.ios2.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StartPrintingDto {
    private String s3_file_path;
    private String before_file_name;
    private String after_file_name;
    private Integer film_code;

//    film_code = data['film_code']
//    s3_file_path = data['s3_file_path']
//    before_file_name = data['before_file_name']
//    after_file_name = data['after_file_name']
}
