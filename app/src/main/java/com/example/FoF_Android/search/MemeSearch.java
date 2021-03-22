package com.example.FoF_Android.search;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemeSearch {

    private List<Data> data;
    private boolean isSuccess;
    private int code;
    private String message;

    public List<Data> getData() { return data;  }
    public boolean isSuccess() { return isSuccess;  }
    public int getCode() { return code; }
    public String getMessage() { return message;  }

    public MemeSearch(List<Data> data, boolean isSuccess, int code, String message) {
        this.data = data;
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public class Data{
        @SerializedName("memeIdx") int memeIdx;
        @SerializedName("imageUrl") String imageUrl;
        @SerializedName("tagIdx") int tagIdx;

        public int getMemeIdx() { return memeIdx;        }
        public String getImageUrl() { return imageUrl;        }
        public int getTagIdx() {return tagIdx;        }
    }
}
