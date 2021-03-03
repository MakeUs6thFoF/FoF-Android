package com.example.FoF_Android.Category;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Category {
    boolean isSuccess;
    int code;
    String message;
    List<Data> data;

    public List<Data> getData() { return data;}
    public boolean isSuccess() { return isSuccess; }
    public int getCode() {  return code; }
    public String getMessage() {  return message; }

    public Category(boolean isSuccess, int code, String message, List<Data> data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public class Data{

        @SerializedName("categoryIdx") int categoryIdx;
        @SerializedName("title") String title;

        public int getCategoryIdx() {  return categoryIdx;    }
        public String getTitle() {   return title;   }

    }

}
