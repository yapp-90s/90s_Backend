package com.yapp.ios2.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddPhotoInAlbumInDto {

    private Long albumUid;
    private Long photoUid;
    private Integer paperNum;
    private Integer sequence;

}
