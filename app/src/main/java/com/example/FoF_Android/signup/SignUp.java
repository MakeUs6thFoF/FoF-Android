package com.example.FoF_Android.signup;

public class SignUp {
    private final String isSuccess;
    private final String code;
    private final String message;


    public String getSuccess() {  return isSuccess;  }

    public String getCode() {   return code; }

    public String getMessage() {  return message; }

    public SignUp(String isSuccess, String code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
