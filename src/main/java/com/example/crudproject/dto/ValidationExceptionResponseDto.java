package com.example.crudproject.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ValidationExceptionResponseDto {

    private LocalDateTime timestamp;
    private int statusCode;
    private String error;
    private String message;
    private String path;
    Map<String,String> fielderror;



    public ValidationExceptionResponseDto(LocalDateTime timestamp, int statusCode, String error, String message, String path,Map<String,String>fielderror) {
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fielderror=fielderror;
    }

    public Map<String,String> getFielderror() {
        return fielderror;
    }

    public void setFielderror(Map<String,String> fielderror) {
        this.fielderror = fielderror;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
