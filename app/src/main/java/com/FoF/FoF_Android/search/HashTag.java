package com.FoF.FoF_Android.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HashTag {

    @SerializedName("data") private Data data;
    private boolean isSuccess;
    private int code;
    private String message;

    public class Data{
        @SerializedName("updateTime") String updateTime;
        @SerializedName("tagList")
        List<TagList> tagList;

        public class TagList{
            @SerializedName("tagIdx") int tagIdx;
            @SerializedName("tagName") String tagName;
            @SerializedName("searchCnt") int searchCnt;   //조회수

            public int getTagIdx() { return tagIdx;  }
            public String getTagName() { return tagName;  }
            public int getSearchCnt() {  return searchCnt;  }

        }

        public List<TagList> getTagList() { return tagList; }
    }

    public Data getData() {return data;}
    public boolean isSuccess() { return isSuccess; }
    public int getCode() {return code; }
    public String getMessage() {return message; }
}
