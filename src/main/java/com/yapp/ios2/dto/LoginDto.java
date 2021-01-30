package com.yapp.ios2.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Email
    @NotNull
    private String emailKakao;
    @Email
    @NotNull
    private String emailApple;
    @Email
    @NotNull
    private String emailGoogle;

    private String phoneNum;
}
