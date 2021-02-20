package com.yapp.ios2.dto;

import lombok.*;


@Getter
@Setter
public class PhotoDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class PhotoDownload {
        private Long photoUid;

    }
}
