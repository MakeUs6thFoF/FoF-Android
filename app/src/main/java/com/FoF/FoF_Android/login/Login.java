package com.FoF.FoF_Android.login;

import com.google.gson.annotations.SerializedName;

public class Login {
    private final boolean isSuccess;
    private final int code;
    private final String message;
    private final String jwt;

    public Boolean getIsSuccess() {
        return isSuccess;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
    public String getJwt(){return jwt;}

    @SerializedName("userInfo")
    UserInfo userinfo;

    public Login(Boolean isSuccess, int code, String message, String jwt) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.jwt = jwt;
    }

    public class UserInfo {
        @SerializedName("idx") Integer idx;
        @SerializedName("email") String email;
        @SerializedName("password") String password;
        @SerializedName("status") String status;

        public Integer getIdx() {
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
