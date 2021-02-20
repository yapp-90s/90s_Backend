package com.yapp.ios2.dto;

import lombok.*;

@Getter
@Setter
public class SmsDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SmsRequestDto {
        private String phoneNumber;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SmsResponseDto {
        private String num;
    }
}
