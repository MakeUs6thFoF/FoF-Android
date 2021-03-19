package com.example.FoF_Android.dialog.model;

public class Copyright {
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


    public Copyright(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
