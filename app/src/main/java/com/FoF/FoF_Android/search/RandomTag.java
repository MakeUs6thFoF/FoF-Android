package com.FoF.FoF_Android.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomTag {
    private List<Data> data;
    private boolean isSuccess;
    private int code;
    private String message;

    public List<Data> getData() { return data;  }
    public boolean isSuccess() { return isSuccess;  }
    public int getCode() { return code; }
    public String getMessage() { return message;  }

    public RandomTag(List<Data> data, boolean isSuccess, int code, String message) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public class Data{
        @SerializedName("tagIdx") int tagIdx;
        @SerializedName("tagName") String tagName;


        public int getTagIdx() { return tagIdx;        }
        public String getTagName() { return tagName;     }

    }
}
