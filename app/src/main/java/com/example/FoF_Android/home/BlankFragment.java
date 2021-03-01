package com.example.FoF_Android.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlankFragment extends Fragment {
    ImageView img;
    Meme meme;
    RetrofitApi api;

    public BlankFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.meme_item, container, false);

        HttpClient client=new HttpClient();
        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        String token=getTOKEN();
        Call<Meme.Data> call = api.getdatas(token,1,1);
        call.enqueue(new Callback<Meme.Data>() {

            @Override
            public void onResponse(Call<Meme.Data> call, Response<Meme.Data> response) {
                if(response.isSuccessful()){
                    Meme.Data items = response.body();
                    img=view.findViewById(R.id.imageView);

                    Glide.with(getContext())
                            .load(items.getImageUrl())
                            .into(img);

                }
                else
                    Log.i("TAG", "onResponse: "+response.code());
            }

            @Override
            public void onFailure(Call<Meme.Data> call, Throwable t) {

                Log.d("MainActivity", t.toString());
            }
        });

        return view;
    }  public String getTOKEN(){
        String token="";
        SharedPreferences prefs = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String value = prefs.getString("token", token);
        System.out.println(value);
        return value;
    }
}