package com.yapp.ios2.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsRequestDto {
    private String phoneNumber;
}