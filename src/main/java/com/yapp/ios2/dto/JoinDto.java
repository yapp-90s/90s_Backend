package com.yapp.ios2.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoinDto {
    private String emailKakao;
    private String emailApple;
    private String emailGoogle;
    private String password;
    private String name;
    private String phone;
}
