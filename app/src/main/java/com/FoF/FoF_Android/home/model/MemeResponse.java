package com.FoF.FoF_Android.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MemeResponse {

    @SerializedName("data")
    @Expose
    private List<Meme.Data> items;

    public List<Meme.Data> getItems(){
        return items;
    }
    public void setItems(List<Meme.Data>items){
        this.items = items;
    }
}

