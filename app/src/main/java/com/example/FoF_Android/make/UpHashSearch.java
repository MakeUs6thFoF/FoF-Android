package com.example.FoF_Android.make;

import com.example.FoF_Android.search.RandomTag;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpHashSearch {

    @SerializedName("data") private List<UpHashSearch.Data> data;
    private boolean isSuccess;
    private int code;
    private String message;

    public class Data{

            @SerializedName("tagIdx") int tagIdx;
            @SerializedName("tagName") String tagName;

            public int getTagIdx() { return tagIdx;  }
            public String getTagName() { return tagName;  }

        public void setTagIdx(int tagIdx) {
            this.tagIdx = tagIdx;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
        public void Data(int tagIdx, String tagName){
                this.tagIdx=tagIdx;
                this.tagName=tagName;
        }
    }


    public List<UpHashSearch.Data> getData() {return data;}
    public boolean isSuccess() { return isSuccess; }
    public int getCode() {return code; }
    public String getMessage() {return message; }
}
