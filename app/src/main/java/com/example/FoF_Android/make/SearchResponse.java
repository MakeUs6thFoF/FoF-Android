package com.example.FoF_Android.make;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {

    @SerializedName("data")
    @Expose
    private List<UpHashSearch.Data> items;
    private final boolean isSuccess;
    private final int code;
    private final String message;
    public List<UpHashSearch.Data> getItems(){
        return items;
    }
    public void setItems(List<UpHashSearch.Data>items){
        this.items = items;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }




    public SearchResponse(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

