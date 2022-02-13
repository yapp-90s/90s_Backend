package com.yapp.ios2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ResponseDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JwtDto{
        private String jwt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UrlDto{
        private String url;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DataDto implements IDto{
        private String data;
    }


}
