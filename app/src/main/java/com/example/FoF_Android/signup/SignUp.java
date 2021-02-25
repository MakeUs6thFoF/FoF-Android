package com.example.FoF_Android.signup;

import com.google.gson.annotations.SerializedName;

public class SignUp {
    private final String isSuccess;
    private final int code;
    private final String message;
    private final String jwt;

    public String getSuccess() {  return isSuccess;  }
    public int getCode() {   return code; }
    public String getMessage() {  return message; }
    public String getJwt() {return jwt;}

    public SignUp(String isSuccess, int code, String message, String jwt) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.jwt = jwt;
    }

    @SerializedName("userInfo")
    UserInfo userinfo;

    public class UserInfo {
        @SerializedName("idx") String idx;
        @SerializedName("email") String email;
        @SerializedName("password") String password;
        @SerializedName("status") String status;

        public String getIdx() {
            return idx;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getStatus() {
            return status;
        }
    }

    public UserInfo getUserinfo() {return userinfo;}
}
