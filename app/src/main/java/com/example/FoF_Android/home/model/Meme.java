package com.example.FoF_Android.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Meme {

    @SerializedName("data")
    private Data data;

    public class Data {
        @SerializedName("memeIdx") Integer memeIdx;
        @SerializedName("userIdx") Integer userIdx;
        @SerializedName("nickname") String nickname;
        @SerializedName("imageUrl") String imageUrl;
        @SerializedName("profileImage") String profileImage;


        @SerializedName("copyright") String copyright;
        @SerializedName("Tag") String Tag;
        public String getCopyright() {
            return copyright;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public Integer getMemeIdx() {
            return memeIdx;
        }

        public Integer getUserIdx() {
            return userIdx;
        }

        public String getNickname() {
            return nickname;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getTag() {
            return Tag;
        }

    }

    public Data getdata() {
        return data;
    }
}

