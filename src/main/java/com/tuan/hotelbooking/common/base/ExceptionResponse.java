package com.tuan.hotelbooking.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tuan.hotelbooking.common.constant.Constant;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String path;
    private String status;
    private int code;
    private Object message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATE_TIME_PATTERN, timezone = Constant.TIMEZONE_VIETNAM)
    private LocalDateTime timestamp;
}