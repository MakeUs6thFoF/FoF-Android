package com.example.FoF_Android.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
    TextView nick;

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
        api = client.getRetrofit().create(RetrofitApi.class);
        String token=getTOKEN();
        System.out.println("확인"+token);
        Call<MemeResponse> call = api.getdata(token,1,3);
        call.enqueue(new Callback<MemeResponse>() {
            @Override
            public void onResponse(Call<MemeResponse> call, Response<MemeResponse> response) {
                if(response.isSuccessful()){
                    List<Meme.Data> items = response.body().getItems();

              //      Log.i("TAG", "onResponse: "+items.get(0).getImageUrl());

                    img=view.findViewById(R.id.imageView);
                    nick=view.findViewById(R.id.textView);

                    nick.setText(items.get(0).getNickname());
                    Glide.with(getActivity())
                            .load(items.get(0).getImageUrl())
                            .into(img);
                }
                else
                    Log.i("TAG", "onResponse: "+response.code());
            }

            @Override
            public void onFailure(Call<MemeResponse> call, Throwable t) {

                Log.d("MainActivity", t.toString());
            }
        });

        return view;
    }

    public String getTOKEN(){
        String token="";
        SharedPreferences prefs = this.getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        String value = prefs.getString("token", token);
        return value;
    }
}