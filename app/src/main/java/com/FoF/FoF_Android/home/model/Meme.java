package com.FoF.FoF_Android.home.model;

import com.google.gson.annotations.SerializedName;

public class Meme {

    @SerializedName("data")
    private Data data;
    private final boolean isSuccess;
    private final int code;
    private final String message;
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
        public void setMemeIdx(Integer memeIdx) {
            this.memeIdx = memeIdx;
        }

        public void setUserIdx(Integer userIdx) {
            this.userIdx = userIdx;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public void setTag(String tag) {
            Tag = tag;
        }
    }
    public Boolean getIsSuccess() {
        return isSuccess;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }

    public Data getdata() {
        return data;
    }



    public Meme(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

