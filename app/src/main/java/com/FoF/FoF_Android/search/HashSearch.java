package com.FoF.FoF_Android.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HashSearch {

    @SerializedName("data") private Data data;
    private boolean isSuccess;
    private int code;
    private String message;

    public class Data{
        @SerializedName("memeCount") int memeCount;
        @SerializedName("memeList") List<memeList> memeList;

        public class memeList{
            @SerializedName("memeIdx") int memeIdx;
            @SerializedName("imageUrl") String imageUrl;

            public int getMemeIdx() { return memeIdx;  }
            public String getImageUrl() { return imageUrl;  }
        }

        public List<memeList> getMemeList() { return memeList; }
        public int getMemeCount() {return memeCount;}
    }

    public Data getData() {return data;}
    public boolean isSuccess() { return isSuccess; }
    public int getCode() {return code; }
    public String getMessage() {return message; }
}
