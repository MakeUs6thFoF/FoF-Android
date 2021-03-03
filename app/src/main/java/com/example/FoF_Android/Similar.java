package com.example.FoF_Android;

import com.google.gson.annotations.SerializedName;

public class Similar {

    @SerializedName("data")
    private Data data;

    public class Data {
        @SerializedName("memeIdx") Integer memeIdx;
        @SerializedName("imageUrl") String imageUrl;

        public Integer getMemeIdx() {
            return memeIdx;
        }


        public String getImageUrl() {
            return imageUrl;
        }

    }

    public Data getdata() {
        return data;
    }
}

