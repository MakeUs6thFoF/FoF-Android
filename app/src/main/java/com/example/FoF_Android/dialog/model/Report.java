package com.example.FoF_Android.dialog.model;

import com.example.FoF_Android.Category.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Report {
    boolean isSuccess;
    int code;
    String message;
    List<Data> data;

    public List<Data> getData() { return data;}
    public boolean isSuccess() { return isSuccess; }
    public int getCode() {  return code; }
    public String getMessage() {  return message; }

    public Report(boolean isSuccess, int code, String message, List<Data> data) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public class Data{

        @SerializedName("idx") int reportidx;
        @SerializedName("reportTagTitle") String title;

        public int getReportidx() {  return reportidx;    }
        public String getTitle() {   return title;   }

    }

}
