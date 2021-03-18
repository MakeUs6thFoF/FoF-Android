package com.example.FoF_Android.detail.model;


public class Like {
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


    public Like(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
