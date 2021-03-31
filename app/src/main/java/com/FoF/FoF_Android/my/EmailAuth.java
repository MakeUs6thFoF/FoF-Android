package com.FoF.FoF_Android.my;

import com.google.gson.annotations.SerializedName;

public class EmailAuth {
    @SerializedName("number") private int number;
    private boolean isSuccess;
    private int code;
    private String message;

    public int getNumber() {return number; }
    public boolean isSuccess() {  return isSuccess; }
    public int getCode() {  return code;  }
    public String getMessage() { return message; }
}
