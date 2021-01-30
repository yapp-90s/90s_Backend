package com.yapp.ios2.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException() {
        super(ErrorCode.EMAIL_NOT_EXIST);
    }
}
