package com.example.demotask4.response;

public class ApiResponse<T>{
    private int status;
    private String message;
    private T data;

    public ApiResponse(int status, String message, T data) {
        this.data = data;
        this.message = message;
        this.status = status;
    }

}
