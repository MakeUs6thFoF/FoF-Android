package com.example.FoF_Android.my;

import com.google.gson.annotations.SerializedName;

public class MyInfo {
    @SerializedName("data")
    private Data data;
    private boolean isSuccess;
    private int code;
    private String message;

    public class Data{
        @SerializedName("userId") private int userId;
        @SerializedName("userEmail") private String userEmail;

        public int getUserId() { return userId;  }
        public String getUserEmail() { return userEmail; }
    }

    public Data getData() {  return data;  }
    public boolean isSuccess() { return isSuccess;  }
    public int getCode() { return code;  }
    public String getMessage() {  return message;  }
}
