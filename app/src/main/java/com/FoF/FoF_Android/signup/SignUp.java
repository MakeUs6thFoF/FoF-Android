package com.FoF.FoF_Android.signup;

public class SignUp {
    private final String isSuccess;
    private final int code;
    private final String message;

    public String getSuccess() {  return isSuccess;  }
    public int getCode() {   return code; }
    public String getMessage() {  return message; }

    public SignUp(String isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }


}
