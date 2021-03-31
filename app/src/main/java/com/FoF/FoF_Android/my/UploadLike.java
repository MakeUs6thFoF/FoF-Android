package com.FoF.FoF_Android.my;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UploadLike {
    @SerializedName("data") private List<Data> data;
    private boolean isSuccess;
    private int code;
    private String message;

    public class Data{
        @SerializedName("memeIdx") int memeIdx;
        @SerializedName("imageUrl") String imageUrl;

        public int getMemeIdx() { return memeIdx;  }
        public String getImageUrl() { return imageUrl;  }
    }

    public List<Data> getData() { return data; }
    public boolean isSuccess() { return isSuccess; }
    public int getCode() {return code; }
    public String getMessage() {return message; }
}
