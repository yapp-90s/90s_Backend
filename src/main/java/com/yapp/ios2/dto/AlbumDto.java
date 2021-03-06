package com.yapp.ios2.dto;

import com.yapp.ios2.vo.Album;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AlbumDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumUid {
        private Long uid;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumPassword{
        private String password;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumInfo {
        private Long albumUid;
        private String name;
        private Long layoutUid;
        private Long coverUid;
        private Integer totPaper;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AlbumOrderInfo{
        private Long albumUid;
        private Integer amount;
        private Long paperType1;
        private Long paperType2;
        private Long postType;
        private String cost;
        private String recipient;
        private String postalCode;
        private String address;
        private String addressDetail;
        private String phoneNum;
        private String message;
    }

}
