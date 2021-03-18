package com.example.FoF_Android.detail.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Detail {
    @SerializedName("data")
    private Data data;

    public class Data {
        @SerializedName("memeDetail")
        private memeDetail detail;

        @SerializedName("similar")
        private List<Similar> data;

        public class memeDetail {
            @SerializedName("memeIdx")
            Integer memeIdx;
            @SerializedName("copyright")
            String copyright;
            @SerializedName("memeTitle")
            String memeTitle;
            @SerializedName("userIdx")
            Integer userIdx;
            @SerializedName("nickname")
            String nickname;
            @SerializedName("imageUrl")
            String imageUrl;
            @SerializedName("profileImage")
            String profileImage;
            @SerializedName("Tag")
            String Tag;

            @SerializedName("likeStatus")
            Integer likeStatus;

            public String getCopyright() {
                return copyright;
            }

            public String getMemeTitle() {
                return memeTitle;
            }

            public Integer getLikeStatus() {
                return likeStatus;
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
        public class Similar {

                @SerializedName("memeIdx") Integer memeIdx;
                @SerializedName("imageUrl") String imageUrl;

                public Integer getMemeIdx() {
                    return memeIdx;
                }


                public String getImageUrl() {
                    return imageUrl;
                }

        }

        public List<Similar> getData() {
            return data;
        }
        public memeDetail getDetail() {
            return detail;
        }

    }

    public Data getdata() {
        return data;
    }
}

