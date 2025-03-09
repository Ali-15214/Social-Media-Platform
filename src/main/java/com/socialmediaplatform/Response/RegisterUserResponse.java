package com.socialmediaplatform.Response;

public class RegisterUserResponse {
    private String message;
    private Long userId;

    public RegisterUserResponse(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RegisterUserResponse{" +
                "message='" + message + '\'' +
                ", userId=" + userId +
                '}';
    }
}