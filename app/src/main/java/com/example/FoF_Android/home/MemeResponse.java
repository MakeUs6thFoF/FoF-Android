package com.example.FoF_Android.home;

import com.example.FoF_Android.home.model.Meme;
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

