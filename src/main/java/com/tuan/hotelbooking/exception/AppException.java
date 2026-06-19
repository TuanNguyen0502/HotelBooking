package com.tuan.hotelbooking.exception;

import com.tuan.hotelbooking.common.base.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    public AppException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }

    public AppException(ErrorCode errorCode, Object... args){
        super(String.format(errorCode.getMessage(), args));
        this.errorCode = errorCode;
        this.message = super.getMessage();
        this.httpStatus = errorCode.getHttpStatus();
    }
}
