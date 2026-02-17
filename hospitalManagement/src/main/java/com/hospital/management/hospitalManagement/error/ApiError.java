package com.hospital.management.hospitalManagement.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {

    private LocalDateTime timestamp;
    private String error;
    private HttpStatus statusCode;

    public ApiError(){this.timestamp=  LocalDateTime.now();}

    public ApiError(HttpStatus statusCode, String error){
        this();
        this.error=error;
        this.statusCode=statusCode;
    }
}
