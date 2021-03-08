package com.example.FoF_Android.search;

import android.media.session.MediaSession;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.FoF_Android.Category.Category;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeelingFragment extends Fragment {

    RetrofitApi api;
    TokenManager gettoken;
    int CategoryIdx;
    ViewPager imageViewPager;


    int memeIdx[] = new int[5];
    List<String> imageUrl = new ArrayList<>();
    int view[] = new int[5];


    public FeelingFragment(int idx) {
        CategoryIdx = idx;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feeling, container, false);

        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken = new TokenManager(getContext());
        getRank(api);

        imageViewPager = (ViewPager)view.findViewById(R.id.topViewPager);
        imageViewPager.setPageMargin(20);
        imageViewPager.setClipToPadding(false);
        imageViewPager.setPadding(100,0,100,0);
        imageViewPager.setAdapter(new MagnifyAnimPageAdapter(getFragmentManager(), getActivity(), imageUrl));
        imageViewPager.setPageTransformer(true, new MagnifyingAnimPageTransInterface(200, 600));

        return view;
    }

    public void getRank(RetrofitApi api){
        String token = gettoken.checklogin(getContext());
        api.getRank(token, CategoryIdx).enqueue(new Callback<CategoryMeme>() {
            @Override
            public void onResponse(Call<CategoryMeme> call, Response<CategoryMeme> response) {
                CategoryMeme ctm = response.body();
                for(int i=0; i< ctm.getData().getMemeList().size(); i++){
                    memeIdx[i] = ctm.getData().getMemeList().get(i).getMemeIdx();
                    imageUrl.add(ctm.getData().getMemeList().get(i).getImageUrl());
                    view[i] = ctm.getData().getMemeList().get(i).getView();


                    //Glide.with(getActivity()).load(imageUrl.get(0)).into(imageView3);
                }

            }

            @Override
            public void onFailure(Call<CategoryMeme> call, Throwable t) {

            }
        });

    }
}