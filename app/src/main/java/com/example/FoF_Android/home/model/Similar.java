package com.example.FoF_Android.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Similar {

    @SerializedName("data")
    private List<Similar.Data> items;

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

    public List<Similar.Data> getdata() {
        return items;
    }
}

