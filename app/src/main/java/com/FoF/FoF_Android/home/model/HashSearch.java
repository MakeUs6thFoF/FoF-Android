package com.FoF.FoF_Android.home.model;

import com.google.gson.annotations.SerializedName;

public class HashSearch {

    @SerializedName("data") private Data data;
    private boolean isSuccess;
    private int code;
    private String message;

    public class Data{

            @SerializedName("memeIdx") int memeIdx;
            @SerializedName("imageUrl") String imageUrl;
            
        @SerializedName("tagIdx") int tagIdx;
        
        public int getTagIdx() {
            return tagIdx;
        }
            public int getMemeIdx() { return memeIdx;  }
            public String getImageUrl() { return imageUrl;  
            }
     

    }

    public Data getData() {return data;}
    public boolean isSuccess() { return isSuccess; }
    public int getCode() {return code; }
    public String getMessage() {return message; }
}
