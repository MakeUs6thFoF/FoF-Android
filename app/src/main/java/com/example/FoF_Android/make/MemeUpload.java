package com.example.FoF_Android.make;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemeUpload {

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

    public MemeUpload(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

