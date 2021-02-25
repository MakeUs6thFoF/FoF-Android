package com.example.FoF_Android;

public class Login {
    private final boolean isSuccess;
    private final int code;
    private final String message;

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Login(int idx, String email, String password, String status, String jwt, Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
