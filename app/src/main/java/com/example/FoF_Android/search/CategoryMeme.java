package com.example.FoF_Android.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryMeme {

    @SerializedName("data") private Data data;
    private boolean isSuccess;
    private int code;
    private String message;

    public class Data{
        @SerializedName("updateTime") String updateTime;
        @SerializedName("memeList") List<MemeList> memeList;

        public class MemeList{
            @SerializedName("memeIdx") int memeIdx;
            @SerializedName("imageUrl") String imageUrl;
            @SerializedName("view") int view;   //조회수

            public int getMemeIdx() { return memeIdx;  }
            public String getImageUrl() { return imageUrl;  }
            public int getView() {  return view;  }

        }

        public List<MemeList> getMemeList() { return memeList; }
    }

    public Data getData() {return data;}
    public boolean isSuccess() { return isSuccess; }

    public int getCode() {return code; }

    public String getMessage() {return message; }
}
