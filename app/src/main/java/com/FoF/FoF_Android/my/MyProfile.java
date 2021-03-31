package com.FoF.FoF_Android.my;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyProfile {
    @SerializedName("data")
    private Data data;
    private boolean isSuccess;
    private int code;
    private String message;

    public class Data {
        @SerializedName("profile")
        private Profile profile;

        @SerializedName("insight")
        private List<Insight> insight;

        public class Profile {
            @SerializedName("profileImage")
            String profileImage;
            @SerializedName("nickname")
            String nickname;
            @SerializedName("acceptedLikeCnt")
            int acceptedLikeCnt;
            @SerializedName("uploadCnt")
            int uploadCnt;
            @SerializedName("likeCnt")
            int likeCnt;

            public String getProfileImage() {  return profileImage;    }
            public String getNickname() {  return nickname;    }
            public int getAcceptedLikeCnt() {  return acceptedLikeCnt;  }
            public int getUploadCnt() {  return uploadCnt;   }
            public int getLikeCnt() {  return likeCnt;   }
        }

        public class Insight {
            @SerializedName("cnt") int cnt;
            @SerializedName("tagName") String tagName;
            public Integer getCnt() { return cnt; }
            public String getTagName() { return tagName; }
        }

        public List<Insight> getInsight() {
            return insight;
        }
        public Profile getProfile() {
            return profile;
        }

    }

    public Data getdata() {
        return data;
    }
}
